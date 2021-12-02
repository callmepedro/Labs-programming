package com.labs.lab7.Client.App;

import com.labs.lab7.Client.Utils.*;

public class Main {

    private static final String DEFAULT_SERVER_ADDR = "localhost";
    private static final int DEFAULT_SERVER_PORT = 4111;
    private static final short DEFAULT_BUFFER_SIZE = 8192;
    private static final long TIMEOUT = 1000;

    private static boolean INIT = false;

    public static void main(String[] args) {

        ConnectionAdjuster adjuster = new ConnectionAdjuster(DEFAULT_SERVER_ADDR, DEFAULT_SERVER_PORT, DEFAULT_BUFFER_SIZE, TIMEOUT);
        adjuster.init();
    }

    public static boolean getINIT(){
        return INIT;
    }

    public static void setINIT(boolean INIT) {
        Main.INIT = INIT;
    }
}
