package com.labs.lab6.Server.Utils;

import com.labs.lab6.common.Request;

import java.util.Scanner;


public class ServerSession {

    private final CommandHandler handler;
    private final RequestsReceiver receiver;

    public ServerSession(RequestsReceiver receiver, CommandHandler handler) {

        this.handler = handler;
        this.receiver = receiver;
    }

    private String serverConsoleListen(){
        System.out.print("[server] > ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void listen(){
        System.out.println(
                "--- Server is listening ---\n" +
                        "Server commands:\n" +
                        "* session_time\n" +
                        "* stop"
        );

        new Thread(() ->
        {
            long startTime = System.currentTimeMillis();

            while (true) {
                switch (serverConsoleListen()) {
                    case "stop":
                        System.exit(0);
                        break;
                    case "session_time":
                        System.out.println(System.currentTimeMillis() - startTime + "ms");
                        break;
                    default:
                        System.out.println("Unknown server command");
                }
            }

        }).start();

        while (true) {
            Request request = receiver.getResponse();
            if (request == null){
                continue;
            }
            handler.executeCommand(request);
        }

    }

}
