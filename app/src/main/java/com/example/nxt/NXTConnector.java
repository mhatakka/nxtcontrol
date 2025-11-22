package com.example.nxt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class NXTConnector {

    private BluetoothSocket socket;
    private InputStream is;
    private OutputStream os;

    // LEGO NXT uses Serial Port Profile (SPP)
    private static final UUID NXT_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public boolean connect(String macAddress) {
        try {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = adapter.getRemoteDevice(macAddress);

            socket = device.createRfcommSocketToServiceRecord(NXT_UUID);
            socket.connect();

            is = socket.getInputStream();
            os = socket.getOutputStream();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public InputStream getInput() { return is; }
    public OutputStream getOutput() { return os; }

    public void close() {
        try { is.close(); } catch (Exception ignore) {}
        try { os.close(); } catch (Exception ignore) {}
        try { socket.close(); } catch (Exception ignore) {}
    }
}
