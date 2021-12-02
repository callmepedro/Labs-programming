package com.labs.lab6.common;

import com.labs.lab6.Client.Utils.CommandStruct;

import java.io.Serializable;

public class Request implements Serializable {

    private final CommandStruct commandStruct;
    private final Object commandArg;

    public Request() {
        this.commandStruct = null;
        this.commandArg = null;
    }
    public Request(CommandStruct commandStruct) {
        this.commandStruct = commandStruct;
        this.commandArg = null;
    }
    public Request(CommandStruct commandStruct, Object commandArg) {
        this.commandStruct = commandStruct;
        this.commandArg = commandArg;
    }

    public CommandStruct getCommandStruct() {
        return commandStruct;
    }

    public Object getCommandArg() {
        return commandArg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "commandStruct=" + commandStruct +
                ", commandArg=" + commandArg +
                '}';
    }
}
