
// FilesFragment.java
package com.example.nxtcontrol;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;

/*

List files
0x01  (Response required)
0x86  (LSGetStatus / FindFirst)
pattern 20 bytes (esim. "*.rxe\0\0\0...")


FindNext
0x01
0x87
handle


DeviceInfo
0x01 0x9B

 */


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class FilesFragment extends Fragment {

    private BluetoothConnectionManager btManager;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> fileList = new ArrayList<>();

    private static final byte DIRECT_COMMAND_REPLY = 0x00;

    private static final byte FIND_FIRST = (byte) 0x86;
    private static final byte FIND_NEXT  = (byte) 0x87;

    public FilesFragment() {
    }

    public static FilesFragment newInstance(BluetoothConnectionManager manager) {
        FilesFragment f = new FilesFragment();
        f.btManager = manager;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_files, container, false);

        ListView listView = v.findViewById(R.id.fileListView);
        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                fileList);
        listView.setAdapter(adapter);

        loadFiles();

        return v;
    }

    private void loadFiles() {
        fileList.clear();

        try {
            InputStream in = btManager.getInputStream();
            OutputStream out = btManager.getOutputStream();

            if (in == null || out == null) {
                Toast.makeText(getContext(), "Not connected to NXT!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send FIND_FIRST "*.???" (all files)
            sendFileCommand(out, FIND_FIRST, "*.*");

            FileEntry entry = readFileReply(in);
            if (entry == null) {
                Toast.makeText(getContext(), "No files found.", Toast.LENGTH_SHORT).show();
                return;
            }

            addEntry(entry);

            // Continue with FIND_NEXT while valid handle
            while (true) {
                sendSimpleCommand(out, FIND_NEXT);
                entry = readFileReply(in);
                if (entry == null) break;
                addEntry(entry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(),
                    "File read error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        adapter.notifyDataSetChanged();
    }

    private void addEntry(FileEntry e) {
        fileList.add(
                e.fileName + "   (" + e.size + " bytes)"
        );
    }


    // ------------------------------
    //  LCP protocol helpers
    // ------------------------------

    private void sendFileCommand(OutputStream out, byte command, String pattern)
            throws IOException {

        byte[] nameBytes = pattern.getBytes();
        byte[] buffer = new byte[2 + 2 + nameBytes.length + 1];

        int len = 2 + nameBytes.length + 1;

        buffer[0] = (byte) len;
        buffer[1] = 0;

        buffer[2] = DIRECT_COMMAND_REPLY;
        buffer[3] = command;

        System.arraycopy(nameBytes, 0, buffer, 4, nameBytes.length);
        buffer[4 + nameBytes.length] = 0;

        out.write(buffer);
        out.flush();
    }

    private void sendSimpleCommand(OutputStream out, byte command)
            throws IOException {

        byte[] buffer = new byte[4];
        buffer[0] = 2;
        buffer[1] = 0;
        buffer[2] = DIRECT_COMMAND_REPLY;
        buffer[3] = command;

        out.write(buffer);
        out.flush();
    }

    private FileEntry readFileReply(InputStream in)
            throws IOException {

        byte size1 = (byte) in.read();
        byte size2 = (byte) in.read();
        if (size1 < 0 || size2 < 0) return null;

        int length = (size1 & 0xFF) | ((size2 & 0xFF) << 8);

        byte[] reply = new byte[length];
        int read = in.read(reply);
        if (read != length) return null;

        if (reply[2] != 0x00) return null;

        int handle = reply[3] & 0xFF;
        String name = new String(reply, 4, 20).trim();

        int low = reply[24] & 0xFF;
        int high = reply[25] & 0xFF;
        int fileSize = low | (high << 8);

        FileEntry e = new FileEntry();
        e.handle = handle;
        e.fileName = name;
        e.size = fileSize;

        return e;
    }


    // ------------------------------
    // Data structure
    // ------------------------------

    private static class FileEntry {
        int handle;
        String fileName;
        int size;
    }
}

