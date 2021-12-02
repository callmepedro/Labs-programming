package com.labs.lab5.AppUtils;

import com.labs.lab5.AppObjects.AstartesCategory;
import com.labs.lab5.Exceptions.IncorrectCommandFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ConsoleManager {

    private static final CommandReader commandReader = new CommandReader();
    private final CommandInvoker commandInvoker;

    public ConsoleManager(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
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
            replyUser("Second argument must be a number");
            return null;
        }
    }

    private AstartesCategory parseCategory(String str){
        try {
            return AstartesCategory.valueOf(str);
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

    /**
     * Start user interaction
     */
    public void run() {
        commandReader.setConsoleMod();

        Integer id;
        AstartesCategory category;
        Boolean loyal;
        String path;

        boolean session = true;
        do {
            replyUserInline("> ", CommandReaderMode.CONSOLE);

            CommandStruct command = getCommandStruct();

            assert command != null;
            if (!command.isHasCommand()){
                if (commandReader.getCommandReaderMod() == CommandReaderMode.FILE) {
                    commandReader.setConsoleMod();
                }
                else {
                    replyUser("Unknown command");
                }
                continue;
            }
            replyUser(String.format("> %s %s", commandReader.getFilePath(), command.getCommand()), CommandReaderMode.FILE);

            switch (command.getCommand()) {
                case "exit":
                    session = false;
                    break;
                case "save":
                    commandInvoker.save();
                    break;
                case "add":
                    commandInvoker.add();
                    break;
                case "info":
                    commandInvoker.info();
                    break;
                case "show":
                    commandInvoker.show();
                    break;
                case "help":
                    commandInvoker.help();
                    break;
                case "print_descending":
                    commandInvoker.printDescending();
                    break;
                case "clear":
                    commandInvoker.clear();
                    break;
                case "remove_lower":
                    if (!commandInvoker.removeLower())
                        replyUser("There is no elements", CommandReaderMode.CONSOLE);
                    break;
                case "remove_last":
                    if(!commandInvoker.removeLast())
                        replyUser("There is no elements", CommandReaderMode.CONSOLE);
                    break;
                case "update":
                    id = parseId(command.getArgument());
                    if (id != null && !commandInvoker.update(id))
                        replyUser("No element with this ID", CommandReaderMode.CONSOLE);
                    break;
                case "remove_by_id":
                    id = parseId(command.getArgument());
                    if (id != null && !commandInvoker.removeById(id))
                        replyUser("No element with this ID", CommandReaderMode.CONSOLE);
                    break;
                case "remove_at":
                    id = parseId(command.getArgument());
                    if (id != null && !commandInvoker.removeAt(id))
                        replyUser("No element with this index", CommandReaderMode.CONSOLE);
                    break;
                case "count_greater_than_category":
                    category = parseCategory(command.getArgument());
                    if (category != null) commandInvoker.countGreaterThanCategory(category);
                    break;
                case "filter_less_than_loyal":
                    loyal = parseLoyal(command.getArgument());
                    if (loyal != null) commandInvoker.filterLessThanLoyal(loyal);
                    break;
                case "execute_script":
                    path = parsePath(command.getArgument());
                    if (path != null) commandInvoker.executeScript(path);
                    break;

                default:
                    replyUser("Unknown command");
            }

        } while (session);

    }

}
