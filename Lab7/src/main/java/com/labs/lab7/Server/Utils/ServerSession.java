package com.labs.lab7.Server.Utils;

import com.labs.lab7.Client.Utils.CommandReader;
import com.labs.lab7.Client.Utils.CommandStruct;
import com.labs.lab7.Server.App.Main;
import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.common.InitData;
import com.labs.lab7.common.Response;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class ServerSession {

    private final ResponseReceiver receiver;
    private final Repository repository;
    private final DatabaseManager databaseManager;

    public ServerSession(ResponseReceiver receiver, DatabaseManager databaseManager) {
        this.receiver = receiver;
        this.databaseManager = databaseManager;
        this.repository = databaseManager.download();
        SpaceMarine.setCounter(getMaxId(repository));
    }

    private int getMaxId(Repository repository){
        int maxId = 0;
        for (int i = 0; i < repository.size(); ++i) {
            int curId = repository.getList().get(i).getId();
            if (curId > maxId){
                maxId = curId;
            }
        }
        return maxId;
    }

    synchronized private CommandStruct serverConsoleListen(){
        com.labs.lab7.Client.Utils.CommandReader commandReader = new CommandReader();
        CommandStruct command;
        try {
            System.out.print("[server] > ");
            command = commandReader.readCommand();

            return command;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (NoSuchElementException e) {
            return new CommandStruct("exit");
        }
    }

    private void changeAddr(String addr) {
        Main.setAddr(addr);
        System.out.println("ADDR updated: " + addr);
    }

    private void changePort(int port) {
        Main.setPort(port);
        System.out.println("PORT updated: " + port);
    }

    private void changeBufferSize(short bufferSize){
        Main.setBufferSize(bufferSize);
        System.out.println("BUFFER_SIZE updated " + bufferSize);
    }

    public void listen(){
        System.out.printf(
                "DEFAULT_ADDR: %s\n" +
                        "DEFAULT_PORT: %s\n" +
                        "DEFAULT_BUFFER_SIZE: %s\n" +
                        "--- Server is listening ---\n" +
                        "Server commands:\n" +
                        "* session_time\n" +
                        "* exit\n",
                Main.getAddr(), Main.getPort(), Main.getBufferSize()
        );


        Runnable console = () -> {
            long startTime = System.currentTimeMillis();

            while (!Thread.currentThread().isInterrupted()) {
                CommandStruct command = serverConsoleListen();

                assert command != null;
                if (!command.isHasCommand()) {
                    continue;
                }

                switch (command.getCommand()) {
                    case "exit":
                        System.exit(0);
                        break;

                    case "session_time":
                        System.out.println(System.currentTimeMillis() - startTime + "ms");
                        break;

                    case "change_addr":
                        changeAddr(command.getArgument());
                        break;

                    case "change_port":
                        try {
                            int port = Integer.parseInt(command.getArgument());
                            changePort(port);
                        } catch (NumberFormatException e) {
                            System.out.println("PORT must be an integer");
                        }
                        break;

                    case "change_buffer_size":
                        try {
                            short bufferSize = Short.parseShort(command.getArgument());
                            changeBufferSize(bufferSize);
                        } catch (NumberFormatException e) {
                            System.out.println("BUFFER_SIZE must be a short");
                        }
                        break;

                    default:
                        System.out.println("Unknown server command");
                }
            }
        };

        Thread consoleThread = new Thread(console);
        consoleThread.start();


        newThreadUponResponse();

    }

    private void newThreadUponResponse() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        UserIdentification userIdentification = new UserIdentification(true);

        while (true) {
            Response response = receiver.getResponse();

            if (response == null)
                continue;

            Runnable r = new Thread(() -> {

                InitData initData = response.getInitData();

                RepliesSendler repliesSendler = new RepliesSendler(initData);
                CommandHandler handler = new CommandHandler(repository, repliesSendler, userIdentification, databaseManager);

                System.out.printf("request {%s:%s -> %s}] (%s)\n[server] > ",
                        response.getInitData().getAddress(),
                        response.getInitData().getPort(),
                        response.getCommandStruct().getCommand(),
                        Thread.currentThread().getName());

                handler.executeCommand(response);
            });

            threadPool.submit(r);
        }
    }

}
