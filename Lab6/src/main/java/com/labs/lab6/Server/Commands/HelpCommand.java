package com.labs.lab6.Server.Commands;

import com.labs.lab6.Server.Utils.CommandInvoker;
import com.labs.lab6.Server.Utils.RepliesSendler;
import com.labs.lab6.common.Request;

import java.lang.reflect.Field;
import java.util.HashSet;

/**
 * Show information about available commands (Located in CommandInvoker)
 */
public class HelpCommand extends AbstractCommand {

    private HashSet<Command> blockingList;
    private String lastData;

    public HelpCommand() {
        super("help", "guide for available commands");
        blockingList = new HashSet<>();
    }

    public String getLastData() {
        return lastData;
    }

    /**
     * Set list of commands not available for user's help guide
     * @param blockingList
     */
    public void setBlockingList(HashSet<Command> blockingList) {
        this.blockingList = blockingList;
    }

    /**
     * Get list of commands not available for user's help guide
     * @return blockingList
     */
    public HashSet<Command> getBlockingList() {
        return blockingList;
    }

    /**
     * Add command for list of commands not available for user's help guide
     * @param command
     * @return true for successful adding / false for failure adding
     */
    public boolean addToBlockingList(Command command) {
        return blockingList.add(command);
    }

    /**
     * Remove command for list of commands not available for user's help guide
     * @param command
     * @return true for successful removing / false for failure removing
     */
    public boolean removeFromBlockingList(Command command) {
        return blockingList.remove(command);
    }

    private String helpLine(Command command, boolean lastCommand) {
        if (lastCommand)
            return String.format("* %s: %s", command.getName(), command.getDescription());
        else
            return String.format("* %s: %s\n", command.getName(), command.getDescription());
    }

    @Override
    public boolean execute(Request request) {  // response.getCommandArg() is the CommandInvoker's exemplar

        StringBuilder manual = new StringBuilder();
        Field[] fields = CommandInvoker.class.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            fields[i].setAccessible(true);
            try {
                Object value = fields[i].get(request.getCommandArg());
                Command command = (Command)value;

                if (!blockingList.contains(command)) {
                    if (i != fields.length - 1)
                        manual.append(helpLine(command, false));
                    else
                        manual.append(helpLine(this, true));
                }
            } catch (IllegalAccessException e) {
                RepliesSendler.sendReply("HelpCommand: Access denied " + e.getMessage());
            }
        }
        lastData = manual.toString();
        return true;
    }
}
