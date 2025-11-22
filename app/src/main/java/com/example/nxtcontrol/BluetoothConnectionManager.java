package com.example.nxtcontrol;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class BluetoothConnectionManager {

    private BluetoothSocket socket;
    private InputStream is;
    private OutputStream os;

    private static final UUID NXT_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public interface Listener {
        void onConnected();
        void onError(String msg);
        void onDisconnected();
    }

    public void connect(BluetoothDevice dev, Listener listener) {
        new Thread(() -> {
            try {
                socket = dev.createRfcommSocketToServiceRecord(NXT_UUID);
                socket.connect();

                is = socket.getInputStream();
                os = socket.getOutputStream();

                listener.onConnected();
            } catch (Exception e) {
                listener.onError(e.toString());
                e.printStackTrace();
            }
        }).start();
    }

    public void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception ignored) {}
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public InputStream getInputStream() { return is; }
    public OutputStream getOutputStream() { return os; }
}
