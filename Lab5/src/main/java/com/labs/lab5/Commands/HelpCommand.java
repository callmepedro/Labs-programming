package com.labs.lab5.Commands;

import com.labs.lab5.AppUtils.CommandInvoker;
import com.labs.lab5.AppUtils.ConsoleManager;

import java.lang.reflect.Field;

/**
 * Show information about available commands (Located in CommandInvoker)
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "guide for available commands");
    }

    private String helpLine(Command command, boolean lastCommand) {
        if (lastCommand)
            return String.format("* %s: %s", command.getName(), command.getDescription());
        else
            return String.format("* %s: %s\n", command.getName(), command.getDescription());
    }

    @Override
    public boolean execute(Object o) {  // Object o is the CommandInvoker's exemplar
        StringBuilder manual = new StringBuilder();
        Field[] fields = CommandInvoker.class.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            fields[i].setAccessible(true);
            try {
                Object value = fields[i].get(o);
                Command command = (Command)value;
                if (i != fields.length - 1)
                    manual.append(helpLine(command, false));
                else
                    manual.append(helpLine(this, true));
            } catch (IllegalAccessException e) {
                ConsoleManager.replyUser("HelpCommand: Access denied " + e.getMessage());
            }
        }
        ConsoleManager.replyUser(manual.toString());
        return true;
    }
}
