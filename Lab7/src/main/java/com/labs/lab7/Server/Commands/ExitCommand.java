package com.labs.lab7.Server.Commands;

import com.labs.lab7.common.Response;

public class ExitCommand extends AbstractCommand{

    private final SaveCommand saveCommand;

    public ExitCommand(SaveCommand saveCommand) {
        super("exit", "save repository and shutdown the client");
        this.saveCommand = saveCommand;
    }

    @Override
    public boolean execute(Response o) {
        return saveCommand.execute(null);
    }
}
