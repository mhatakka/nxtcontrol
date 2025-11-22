package com.example.nxtcontrol;


import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {

    public interface OnDeviceClickListener {
        void onDeviceClick(BluetoothDevice device);
    }

    private List<BluetoothDevice> devices;
    private Map<String, String> statusMap;
    private OnDeviceClickListener listener;

    public DeviceListAdapter(List<BluetoothDevice> devices,
                             Map<String, String> statusMap,
                             OnDeviceClickListener listener) {
        this.devices = devices;
        this.statusMap = statusMap;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, status;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.deviceName);
            address = itemView.findViewById(R.id.deviceAddress);
            status = itemView.findViewById(R.id.deviceStatus);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                listener.onDeviceClick(devices.get(pos));
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int pos) {
        BluetoothDevice d = devices.get(pos);

        h.name.setText(d.getName());
        h.address.setText(d.getAddress());

        String st = statusMap.getOrDefault(d.getAddress(), "Not connected");
        h.status.setText(st);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}
