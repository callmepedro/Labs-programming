package com.labs.lab6.Server.Commands;

import com.labs.lab6.common.Request;

public class ExitCommand extends AbstractCommand{

    private final SaveCommand saveCommand;

    public ExitCommand(SaveCommand saveCommand) {
        super("exit", "save repository and shutdown the client");
        this.saveCommand = saveCommand;
    }

    @Override
    public boolean execute(Request o) {
        if (saveCommand.execute(null))
            return true;
        return false;
    }
}
