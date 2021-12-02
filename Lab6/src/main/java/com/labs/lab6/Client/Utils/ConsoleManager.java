package com.labs.lab6.Client.Utils;

import com.labs.lab6.Client.App.Main;
import com.labs.lab6.common.AppObjects.AstartesCategory;
import com.labs.lab6.common.Exceptions.IncorrectCommandFormatException;
import com.labs.lab6.common.Exceptions.ExecuteScriptRecursionException;
import com.labs.lab6.common.Request;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class ConsoleManager {

    private static final CommandReader commandReader = new CommandReader();
    private final RequestsSendler sendler;
    private final ResponsesReceiver receiver;
    private final MarineCreator marineCreator;
    private final HashSet<String> executeScriptCommandsPull = new HashSet<>();


    public ConsoleManager(RequestsSendler requestsSendler,
                          ResponsesReceiver responsesReceiver, MarineCreator marineCreator) {
        this.sendler = requestsSendler;
        this.receiver = responsesReceiver;
        this.marineCreator = marineCreator;
    }

    public static CommandReader getCommandReader(){
        return commandReader;
    }

    public static CommandStruct getCommandStruct() {
        try {
            return commandReader.readCommand();
        } catch (FileNotFoundException e){
            replyUser("File not found");
        } catch (IOException e){
            replyUser("File input error");
        } catch (IncorrectCommandFormatException e) {
            replyUser("Incorrect format of command", CommandReaderMode.CONSOLE);
        }
        return null;
    }

    public static void replyUser (String msg) {
        System.out.println(msg);
    }
    public static void replyUserInline(String msg){
        System.out.print(msg);
    }
    public static void replyUser (String msg, CommandReaderMode mode){
        if (commandReader.getCommandReaderMod() == mode) {
            System.out.println(msg);
        }
    }
    public static void replyUserInline(String msg, CommandReaderMode mode){
        if (commandReader.getCommandReaderMod() == mode) {
            System.out.print(msg);
        }
    }

    private Integer parseId(String str){
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e){
            replyUser("Second argument must be an Integer");
            return null;
        }
    }

    private AstartesCategory parseCategory(String str){
        try {
            return AstartesCategory.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            replyUser("There is no such category");
        }
        return null;
    }

    private Boolean parseLoyal(String str){
        if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(str);
        }
        replyUser("Argument must be boolean");
        return null;
    }

    private String parsePath(String str){
        if (str == null || str.equals("")) {
            replyUser("Second argument must be the path");
            return null;
        }
        return str;
    }

    private void execCommand(CommandStruct command, Object commandArg) {
        Request request = new Request(command, commandArg);
        if(sendler.sendRequest(request)) {
            String msg = receiver.getResponse();
            if (msg == null) return;
            if (!msg.equals("")) replyUser(msg);
        }
    }

    /**
     * Start user interaction
     */
    public void run() {
        commandReader.setConsoleMod();

        Integer id;
        AstartesCategory category;
        Boolean loyal;
        String path;
        Request request;
        String msg;

        boolean session = true;
        do {
            replyUserInline("> ", CommandReaderMode.CONSOLE);

            CommandStruct command = getCommandStruct();

            assert command != null;
            if (!command.isHasCommand()){
                if (commandReader.getCommandReaderMod() == CommandReaderMode.FILE) {
                    commandReader.setConsoleMod();
                }
                continue;
            }
            replyUser(String.format("> %s %s", commandReader.getFilePath(), command.getCommand()), CommandReaderMode.FILE);

            switch (command.getCommand()) {
                case "exit":
                    CommandStruct exitCommand = new CommandStruct("exit");
                    request = new Request(exitCommand);
                    if (sendler.sendRequest(request)) {
                        msg = receiver.getResponse();
                        if (msg != null && !msg.trim().equals("false")) {
                            session = false;
                            replyUser(msg);
                        }
                        else {
                            replyUserInline("Save error! Repository not saved. Shut it anyway? ");
                            Scanner scanner = new Scanner(System.in);
                            String ans = scanner.nextLine();
                            if (ans.equals("y") || ans.equals("yes")){
                                session = false;
                            }
                        }
                    }
                    Main.setINIT(false);
                    break;

                case "clear":
                case "print_descending":
                case "help":
                case "show":
                case "info":
                case "remove_last":
                    execCommand(command, null);
                    break;

                case "remove_by_id":
                case "remove_at":
                    id = parseId(command.getArgument());
                    if (id != null)
                        execCommand(command, null);
                    break;

                case "update":
                    CommandStruct containsCommand = new CommandStruct("contains", command.getArgument());
                    request = new Request(containsCommand);
                    id = parseId(command.getArgument());

                    if (id != null && sendler.sendRequest(request)
                            && receiver.getResponse().trim().equals("true"))
                        execCommand(command, marineCreator.create());
                    else if (id != null)
                        replyUser("No element with this ID");
                    break;

                case "add":
                case "remove_lower":
                    execCommand(command, marineCreator.create());
                    break;

                case "count_greater_than_category":
                    category = parseCategory(command.getArgument());
                    if (category != null)
                        execCommand(command, category);
                    break;

                case "filter_less_than_loyal":
                    loyal = parseLoyal(command.getArgument());
                    if (loyal != null)
                        execCommand(command, loyal);
                    break;

                case "execute_script":
                    path = parsePath(command.getArgument());
                    if (path != null)
                        executeScript(path);
                    break;

                default:
                    replyUser("Unknown command");
                    commandReader.setConsoleMod();
            }

        } while (session);

    }

    public void executeScript(String path) {

        try {
            if (getCommandReader().getCommandReaderMod() == CommandReaderMode.CONSOLE) {
                executeScriptCommandsPull.clear();
            }
            else if (executeScriptCommandsPull.contains(path)) {
                throw new ExecuteScriptRecursionException("This command causes infinite recursion");
            }
            else {
                executeScriptCommandsPull.add(path);
            }
            ConsoleManager.getCommandReader().setFileMod(path);

        } catch (ExecuteScriptRecursionException e){
            replyUser(e.getMessage());
        } catch (FileNotFoundException e) {
            replyUser("File not found");
        }
    }

}
