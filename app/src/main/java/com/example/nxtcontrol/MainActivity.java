// Skeleton code for Android "NXT Control Center" equivalent
// Using old support libraries (compatible with Android Studio 3.2.1)

// MainActivity.java
package com.example.nxtcontrol;




// import android.support.design.widget.TabLayout;
// import android.support.v4.view.ViewPager;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter btAdapter;
    private RecyclerView deviceList;
    private DeviceListAdapter adapter;
    private BluetoothConnectionManager connection;

    private List<BluetoothDevice> nxtDevices = new ArrayList<>();
    private Map<String, String> statusMap = new HashMap<>();

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("config", MODE_PRIVATE);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        connection = new BluetoothConnectionManager();

        deviceList = findViewById(R.id.deviceList);
        deviceList.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DeviceListAdapter(nxtDevices, statusMap, this::onDeviceSelected);
        deviceList.setAdapter(adapter);

        findViewById(R.id.btnSearch).setOnClickListener(v -> scanDevices());
        findViewById(R.id.btnDisconnect).setOnClickListener(v -> disconnect());


        // --- ViewPager + Tabs (Fragments) setup ---
        // Etsi ViewPager ja TabLayout (olettaen että ne ovat activity_main.xml:ssä)
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Käytä samaa ViewPagerAdapter/SectionsPagerAdapter-luokkaa jonka loimme aiemmin
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Lisää fragmentit pageriin. FilesFragment tarvitsee BluetoothConnectionManager -> lähetetään newInstanceillä
        pagerAdapter.addFragment(FilesFragment.newInstance(connection), "Files");
        pagerAdapter.addFragment(new SettingsFragment(), "Settings");
        pagerAdapter.addFragment(new MonitorFragment(), "Monitor");
        pagerAdapter.addFragment(new ControlFragment(), "Control");
        pagerAdapter.addFragment(new MiscFragment(), "Misc");

        // Aseta adapteri ViewPageriin ja kytke TabLayout
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        scanDevices(); // initial load
    }

    private void scanDevices() {
        nxtDevices.clear();

        for (BluetoothDevice dev : btAdapter.getBondedDevices()) {
            if (dev.getName() != null && dev.getName().startsWith("NXT")) {
                nxtDevices.add(dev);
                statusMap.put(dev.getAddress(), "Not connected");
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void onDeviceSelected(BluetoothDevice device) {
        statusMap.put(device.getAddress(), "Connecting...");
        adapter.notifyDataSetChanged();

        connection.connect(device, new BluetoothConnectionManager.Listener() {
            @Override
            public void onConnected() {
                runOnUiThread(() -> {
                    statusMap.put(device.getAddress(), "Connected");

                    prefs.edit().putString("lastDevice", device.getAddress()).apply();

                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onError(String msg) {
                runOnUiThread(() -> {
                    statusMap.put(device.getAddress(), "Error: " + msg);
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onDisconnected() {
                runOnUiThread(() -> {
                    statusMap.put(device.getAddress(), "Disconnected");
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void disconnect() {
        connection.disconnect();
        for (BluetoothDevice d : nxtDevices) {
            statusMap.put(d.getAddress(), "Not connected");
        }
        adapter.notifyDataSetChanged();
    }
}






