package com.labs.lab7.Client.Utils;

import com.labs.lab7.Client.App.Main;
import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.common.InitData;
import com.labs.lab7.common.Response;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.Scanner;

public class ConnectionAdjuster {

    private final String SALT = "^boy$next##do*r";

    private String SERVER_ADDR;
    private int SERVER_PORT;
    private int CLIENT_PORT;
    private short BUFFER_SIZE;
    private long TIMEOUT;
    private String LOGIN;
    private String PASSWORD;

    private RequestsSerializator serializator;
    private RequestsReceiver receiver;
    private ResponsesSendler sendler;

    private InitData initData;
    private Response response;
    private Boolean logged;

    public ConnectionAdjuster(String SERVER_ADDR, int SERVER_PORT, short BUFFER_SIZE, long TIMEOUT){
        this.SERVER_ADDR = SERVER_ADDR;
        this.SERVER_PORT = SERVER_PORT;
        this.BUFFER_SIZE = BUFFER_SIZE;
        this.TIMEOUT = TIMEOUT;
        this.SERVER_ADDR = "localhost";
        this.logged = false;
    }


    private void changeAddr(String addr) {
        this.SERVER_ADDR = addr;
        System.out.println("ADDR updated: " + addr);
    }

    private void changeServerPort(int port){
        this.SERVER_PORT = port;
        System.out.println("SERVER_PORT updated: " + port);
    }

    private void changeClientPort(int port){
        this.CLIENT_PORT = port;
        System.out.println("CLIENT_PORT updated: " + port);
    }

    private void changeBufferSize(short bufferSize){
        this.BUFFER_SIZE = bufferSize;
        System.out.println("BUFFER_SIZE updated " + bufferSize);
    }

    private void changeTimeout(long timeout){
        this.TIMEOUT = timeout;
        System.out.println("TIMEOUT updated " + timeout);
    }

    private void manageAfterAuth() throws IOException {
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

                case "auth":
                    manageAuth();
                    break;

                case "register":
                    manageRegister();
                    break;

                case "change_addr":
                    changeAddr(command.getArgument());
                    break;

                case "change_server_port":
                    try{
                        int port = Integer.parseInt(command.getArgument());
                        changeServerPort(port);
                    } catch (NumberFormatException e){
                        System.out.println("SERVER_PORT must be an integer");
                    }
                    break;

                case "change_client_port":
                    try{
                        int port = Integer.parseInt(command.getArgument());
                        changeClientPort(port);
                    } catch (NumberFormatException e){
                        System.out.println("CLIENT_PORT must be an integer");
                    }
                    break;

                case "change_buffer_size":
                    try{
                        short bufferSize = Short.parseShort(command.getArgument());
                        changeBufferSize(bufferSize);
                    } catch (NumberFormatException e){
                        System.out.println("BUFFER_SIZE must be a short");
                    }
                    break;

                case "change_timeout":
                    try{
                        long timeout = Long.parseLong(command.getArgument());
                        changeTimeout(timeout);
                    } catch (NumberFormatException e){
                        System.out.println("TIMEOUT must be a long");
                    }
                    break;

                case "connect":
                    if (!logged){
                        System.out.println("User is not logged.");
                        continue;
                    }

                    initData = new InitData(CLIENT_PORT, SERVER_ADDR, LOGIN, PASSWORD);
                    response = new Response(initData, new CommandStruct("init"));
                    int marinesCounter;

                    if (sendler.sendResponse(response)) {
                        String ans = receiver.getRequest();
                        if (ans != null) {
                            try {
                                marinesCounter = Integer.parseInt(ans.trim());
                                SpaceMarine.setCounter(marinesCounter);
                                MarineCreator marineCreator = new MarineCreator();

                                ConsoleManager consoleManager =
                                        new ConsoleManager(sendler, receiver, marineCreator, initData);

                                String reply = String.format("--- SERVER CONNECTION ESTABLISHED ---\n" +
                                                "User: %s\n" +
                                                "ADDR: %s\n" +
                                                "PORT: %s\n" +
                                                "BUFFER_SIZE: %s\n" +
                                                "TIMEOUT: %sms",
                                        LOGIN, SERVER_ADDR, SERVER_PORT, BUFFER_SIZE, TIMEOUT);

                                ConsoleManager.replyUser(reply);
                                Main.setINIT(true);
                                consoleManager.run();

                            } catch (NumberFormatException e){
                                System.out.println("Connection refused. " + e.getMessage());
                            }
                        }
                    }
                    break;

                default:
                    System.out.println("Unknown command");
            }

        } while (session);
    }

    private String enterField(String msg) throws IOException {
        CommandReader commandReader;
        CommandStruct command;
        boolean notNull;
        do {
            notNull = true;
            System.out.print(msg);
            commandReader = new CommandReader();
            command = commandReader.readCommand();
            if (command.getCommand() == null) {
                System.out.println("Field must be not null");
                notNull = false;
            }
        } while (!notNull);

        return command.getCommand().trim();
    }

    private void manageAuth() throws IOException {
        LOGIN = enterField("Enter login > ");
        String unsafePassword = enterField("Enter password > ");

        PASSWORD = SecurePassword.get_SHA_512(unsafePassword, SALT);

        initData = new InitData(CLIENT_PORT, SERVER_ADDR, LOGIN, PASSWORD);
        response = new Response(initData, new CommandStruct("auth"));

        if (sendler.sendResponse(response)){
            String ans = receiver.getRequest();
            if (ans != null) {
                logged = Boolean.parseBoolean(ans.trim());
                if (logged)
                    System.out.println("Successful authorization. Current user: " + LOGIN + "\n");
                else
                    System.out.println("Authorization failed. Check correctness of login and password.\n");
            }
        }
    }

    private void manageRegister() throws IOException {
        LOGIN = enterField("Enter login > ");
        String unsafePassword = enterField("Enter password > ");

        while (!unsafePassword.equals(enterField("Confirm password > "))){
            System.out.println("PASSWORD MISMATCH");
            unsafePassword = enterField("Enter password > ");
        }

        PASSWORD = SecurePassword.get_SHA_512(unsafePassword, SALT);

        initData = new InitData(CLIENT_PORT, SERVER_ADDR, LOGIN, PASSWORD);
        response = new Response(initData, new CommandStruct("register"));

        if (sendler.sendResponse(response)){
            String ans = receiver.getRequest();
            if (ans != null) {
                logged = Boolean.parseBoolean(ans.trim());
                if (logged)
                    System.out.println("Successful registration. Current user: " + LOGIN + "\n");
                else
                    System.out.println("Registration failed. Check if user already exist.\n");
            }
        }
    }

    public void init() {
        System.out.printf(
                "--- CLIENT-SERVER AUTHORIZATION ---\n" +
                        "DEFAULT_SERVER_ADDR: %s\n" +
                        "DEFAULT_SERVER_PORT: %s\n" +
                        "DEFAULT_BUFFER_SIZE: %s\n" +
                        "DEFAULT_TIMEOUT: %sms\n\n",
                SERVER_ADDR, SERVER_PORT, BUFFER_SIZE, TIMEOUT);

        try {
            boolean portAccepted = false;
            do {
                try {
                    String strClientPort = enterField("Enter client port > ");
                    CLIENT_PORT = Integer.parseInt(strClientPort);
                    portAccepted = true;
                } catch (NumberFormatException e){
                    System.out.println("PORT must be an integer");
                }
            } while (!portAccepted);

            serializator = new RequestsSerializator();
            sendler = new ResponsesSendler(serializator, SERVER_ADDR, SERVER_PORT, BUFFER_SIZE);
            receiver = new RequestsReceiver(CLIENT_PORT, BUFFER_SIZE, TIMEOUT);

            System.out.println(
                    "\nAdjuster commands:\n" +
                            "* auth\n" +
                            "* register\n" +
                            "* change_addr {string}\n" +
                            "* change_server_port {number}\n" +
                            "* change_client_port {number}\n" +
                            "* change_buffer_size {number}\n" +
                            "* change_timeout {number}\n" +
                            "* connect\n" +
                            "* exit"
            );
            manageAfterAuth();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
