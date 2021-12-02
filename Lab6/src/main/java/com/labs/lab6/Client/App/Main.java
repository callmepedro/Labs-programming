package com.labs.lab6.Client.App;

import com.labs.lab6.Client.Utils.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.UnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    private static final String DEFAULT_ADDR = "localhost";
    private static final int DEFAULT_PORT = 4114;
    private static final short DEFAULT_BUFFER_SIZE = 8192;
    private static final long TIMEOUT = 1000;

    private static boolean INIT = false;

    public static void main(String[] args) {

        ConnectionAdjuster adjuster = new ConnectionAdjuster(DEFAULT_ADDR, DEFAULT_PORT, DEFAULT_BUFFER_SIZE, TIMEOUT);
        adjuster.init();
    }

    public static boolean getINIT(){
        return INIT;
    }

    public static void setINIT(boolean INIT) {
        Main.INIT = INIT;
    }
}
