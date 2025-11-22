package com.example.nxt;


import java.io.InputStream;
import java.io.OutputStream;

public class NXTCommand {

    private final OutputStream os;
    private final InputStream is;

    public NXTCommand(OutputStream os, InputStream is) {
        this.os = os;
        this.is = is;
    }

    // Lähetä LCP-komento ilman vastausta (no reply)
    public synchronized void sendDirectCommand(byte[] payload) throws Exception {

        int len = payload.length;

        byte LSB = (byte)(len & 0xFF);
        byte MSB = (byte)((len >> 8) & 0xFF);

        os.write(LSB);
        os.write(MSB);
        os.write(payload);
        os.flush();
    }

    // Komento: setOutputState
    public void setOutputState(
            int port,
            int power,
            int mode,
            int regulationMode,
            int turnRatio,
            int runState,
            int tachoLimit) throws Exception {

        byte[] cmd = new byte[12];
        cmd[0] = (byte)0x80;      // DIRECT_COMMAND_NOREPLY
        cmd[1] = 0x04;            // SET_OUTPUT_STATE
        cmd[2] = (byte)port;
        cmd[3] = (byte)power;
        cmd[4] = (byte)mode;
        cmd[5] = (byte)regulationMode;
        cmd[6] = (byte)turnRatio;
        cmd[7] = (byte)runState;
        cmd[8] = (byte)(tachoLimit & 0xFF);
        cmd[9] = (byte)((tachoLimit >> 8) & 0xFF);
        cmd[10] = (byte)((tachoLimit >> 16) & 0xFF);
        cmd[11] = (byte)((tachoLimit >> 24) & 0xFF);

        sendDirectCommand(cmd);
    }
}
