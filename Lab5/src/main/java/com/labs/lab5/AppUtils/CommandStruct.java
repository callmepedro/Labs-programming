package com.labs.lab5.AppUtils;

public class CommandStruct {
    private final String first;
    private final String second;
    private final boolean hasCommand;
    private final boolean hasArgument;

    public CommandStruct() {
        this.hasCommand = false;
        this.hasArgument = false;
        this.first = null;
        this.second = null;
    }
    public CommandStruct(String first) {
        this.hasCommand = true;
        this.hasArgument = false;
        this.first = first;
        this.second = null;
    }
    public CommandStruct(String first, String second) {
        this.hasCommand = true;
        this.hasArgument = true;
        this.first = first;
        this.second = second;
    }

    /**
     * Get first part of instruction (usually command)
     * @return command
     */
    public String getCommand() {
        return first;
    }

    /**
     * Get second part of instruction (usually argument of command)
     * @return command
     */
    public String getArgument() {
        return second;
    }

    /**
     * Check for command
     */
    public Boolean isHasCommand() {
        return hasCommand;
    }

    /**
     * Check for argument
     */
    public Boolean isHasArgument() {
        return hasArgument;
    }


}
