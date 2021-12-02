package com.labs.lab7.common;

import com.labs.lab7.Client.Utils.CommandStruct;

import java.io.Serializable;

public class Response implements Serializable {

    private final CommandStruct commandStruct;
    private final Object commandArg;
    private final InitData initData;

    public Response(InitData initData) {
        this.initData = initData;
        this.commandStruct = null;
        this.commandArg = null;
    }
    public Response(InitData initData, CommandStruct commandStruct) {
        this.initData = initData;
        this.commandStruct = commandStruct;
        this.commandArg = null;
    }
    public Response(InitData initData, CommandStruct commandStruct, Object commandArg) {
        this.commandStruct = commandStruct;
        this.commandArg = commandArg;
        this.initData = initData;
    }

    public CommandStruct getCommandStruct() {
        return commandStruct;
    }

    public Object getCommandArg() {
        return commandArg;
    }

    public InitData getInitData(){
        return initData;
    }

    @Override
    public String toString() {
        return "Response{" +
                "commandStruct=" + commandStruct +
                ", commandArg=" + commandArg +
                ", initData=" + initData +
                '}';
    }
}
