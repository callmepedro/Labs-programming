package com.labs.lab6.Client.Utils;

import com.labs.lab6.Client.App.Main;
import com.labs.lab6.common.AppObjects.SpaceMarine;
import com.labs.lab6.common.Request;

import java.io.IOException;
import java.net.*;

public class ConnectionAdjuster {

    private String ADDR;
    private int PORT;
    private short BUFFER_SIZE;
    private long TIMEOUT;

    public ConnectionAdjuster(String ADDR, int PORT, short BUFFER_SIZE, long TIMEOUT){
        this.ADDR = ADDR;
        this.PORT = PORT;
        this.BUFFER_SIZE = BUFFER_SIZE;
        this.TIMEOUT = TIMEOUT;
    }

    private void changeAddr(String addr) {
        this.ADDR = addr;
        System.out.println("ADDR updated: " + addr);
    }

    private void changePort(int port){
        this.PORT = port;
        System.out.println("PORT updated: " + port);
    }

    private void changeBufferSize(short bufferSize){
        this.BUFFER_SIZE = bufferSize;
        System.out.println("BUFFER_SIZE updated " + bufferSize);
    }

    private void changeTimeout(long timeout){
        this.TIMEOUT = timeout;
        System.out.println("TIMEOUT updated " + timeout);
    }

    private void manage() throws IOException {
        CommandReader commandReader = new CommandReader();
        CommandStruct command;
        boolean session = true;

        do {
            System.out.print("[adjuster] > ");
            command = commandReader.readCommand();

            assert command != null;
            if (!command.isHasCommand()){
                continue;
            }

            switch (command.getCommand()){
                case "exit":
                    session = false;
                    break;

                case "change_addr":
                    changeAddr(command.getArgument());
                    break;

                case "change_port":
                    try{
                        int port = Integer.parseInt(command.getArgument());
                        if (port <= 0)
                            throw new NumberFormatException();
                        changePort(port);
                    } catch (NumberFormatException e){
                        System.out.println("PORT must be a positive integer");
                    }
                    break;

                case "change_buffer_size":
                    try{
                        short bufferSize = Short.parseShort(command.getArgument());
                        if (bufferSize <= 0)
                            throw new NumberFormatException();
                        changeBufferSize(bufferSize);
                    } catch (NumberFormatException e){
                        System.out.println("BUFFER_SIZE must be a positive short");
                    }
                    break;

                case "change_timeout":
                    try{
                        long timeout = Long.parseLong(command.getArgument());
                        if (timeout <= 0)
                            throw new NumberFormatException();
                        changeTimeout(timeout);
                    } catch (NumberFormatException e){
                        System.out.println("TIMEOUT must be a positive long");
                    }
                    break;

                case "connect":
                    RequestsSerializator serializator = new RequestsSerializator(BUFFER_SIZE);
                    RequestsSendler requestsSendler =
                            new RequestsSendler(serializator, ADDR, PORT, BUFFER_SIZE);

                    ResponsesReceiver responsesReceiver = new ResponsesReceiver(PORT, BUFFER_SIZE, TIMEOUT);

                    Request request = new Request(new CommandStruct("init"));
                    int marinesCounter;

                    if (requestsSendler.sendRequest(request)){

                        String ans = responsesReceiver.getResponse();
                        if (ans != null) {
                            marinesCounter = Integer.parseInt(ans.trim());
                            SpaceMarine.setCounter(marinesCounter);
                            MarineCreator marineCreator = new MarineCreator();

                            ConsoleManager consoleManager = new ConsoleManager(requestsSendler, responsesReceiver, marineCreator);

                            String reply = String.format("--- SERVER CONNECTION ESTABLISHED ---\n" +
                                            "ADDR: %s\n" +
                                            "PORT: %s\n" +
                                            "BUFFER_SIZE: %s\n" +
                                            "TIMEOUT: %sms",
                                    ADDR, PORT, BUFFER_SIZE, TIMEOUT);

                            ConsoleManager.replyUser(reply);
                            Main.setINIT(true);
                            consoleManager.run();
                        }
                    }
                    break;

                default:
                    System.out.println("Unknown command");
            }

        } while (session);
    }

    public void init() {
        System.out.printf(
                "--- CLIENT-SERVER INITIALIZATION ---\n" +
                        "DEFAULT_ADDR: %s\n" +
                        "DEFAULT_PORT: %s\n" +
                        "DEFAULT_BUFFER_SIZE: %s\n" +
                        "DEFAULT_TIMEOUT: %sms\n\n",
                ADDR, PORT, BUFFER_SIZE, TIMEOUT);


        try {
            System.out.println(
                    "Adjuster commands:\n" +
                            "* change_addr {string}\n" +
                            "* change_port {number}\n" +
                            "* change_buffer_size {number}\n" +
                            "* change_timeout {number}\n" +
                            "* connect\n" +
                            "* exit"
            );
            manage();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
