package com.labs.lab7.Client.Utils;

import com.labs.lab7.Client.App.Main;
import com.labs.lab7.common.AppObjects.AstartesCategory;
import com.labs.lab7.common.Exceptions.IncorrectCommandFormatException;
import com.labs.lab7.common.Exceptions.ExecuteScriptRecursionException;
import com.labs.lab7.common.InitData;
import com.labs.lab7.common.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class ConsoleManager {

    private static final CommandReader commandReader = new CommandReader();
    private final ResponsesSendler requestsSendler;
    private final RequestsReceiver responsesReceiver;
    private final MarineCreator marineCreator;
    private final InitData initData;
    private final HashSet<String> executeScriptCommandsPull = new HashSet<>();


    public ConsoleManager(ResponsesSendler requestsSendler, RequestsReceiver responsesReceiver,
                          MarineCreator marineCreator, InitData initData) {
        this.requestsSendler = requestsSendler;
        this.responsesReceiver = responsesReceiver;
        this.marineCreator = marineCreator;
        this.initData = initData;
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
        Response response = new Response(initData, command, commandArg);
        if(requestsSendler.sendResponse(response)) {
            String msg = responsesReceiver.getRequest();
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
        Response request;
        String msg;

        boolean session = true;
        do {
            replyUserInline(String.format("[%s@] > ", initData.getLogin()), CommandReaderMode.CONSOLE);

            CommandStruct command = getCommandStruct();

            assert command != null;
            if (!command.isHasCommand()){
                if (commandReader.getCommandReaderMod() == CommandReaderMode.FILE) {
                    commandReader.setConsoleMod();
                }
                continue;
            }
            replyUser(String.format(
                    "[%s@%s]> %s", initData.getLogin(), commandReader.getFilePath(),
                    command.getCommand()), CommandReaderMode.FILE);

            switch (command.getCommand()) {
                case "exit":
                    CommandStruct exitCommand = new CommandStruct("exit");
                    request = new Response(initData, exitCommand);
                    if (requestsSendler.sendResponse(request)) {
                        msg = responsesReceiver.getRequest();
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
                    request = new Response(initData, containsCommand);
                    id = parseId(command.getArgument());

                    if (id != null && requestsSendler.sendResponse(request)){
                        String response = responsesReceiver.getRequest().trim();
                        if (response.equals("true")) {
                            execCommand(command, marineCreator.create(initData));
                            break;
                        }
                        replyUser(response);
                    }
                    break;

                case "add":
                case "remove_lower":
                    execCommand(command, marineCreator.create(initData));
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
