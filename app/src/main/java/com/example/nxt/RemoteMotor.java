package com.example.nxt;


public class RemoteMotor {

    public static final int MOTORON = 0x01;
    public static final int BRAKE   = 0x02;
    public static final int REGULATED = 0x04;

    public static final int REGULATION_MODE_MOTOR_SPEED = 0x01;
    public static final int MOTOR_RUN_STATE_IDLE = 0x00;
    public static final int MOTOR_RUN_STATE_RUNNING = 0x20;

    private final NXTCommand cmd;
    private final int port;
    private int power = 60;

    public RemoteMotor(NXTCommand cmd, int port) {
        this.cmd = cmd;
        this.port = port;
    }

    public void setPower(int p) {
        this.power = p;
    }

    public void forward() {
        try {
            cmd.setOutputState(
                    port,
                    power,
                    MOTORON + BRAKE + REGULATED,
                    REGULATION_MODE_MOTOR_SPEED,
                    0,
                    MOTOR_RUN_STATE_RUNNING,
                    0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            cmd.setOutputState(
                    port,
                    0,
                    BRAKE,
                    0,
                    0,
                    MOTOR_RUN_STATE_IDLE,
                    0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
