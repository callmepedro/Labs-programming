package com.labs.lab5.Commands;

import com.labs.lab5.AppUtils.ConsoleManager;
import com.labs.lab5.AppUtils.Repository;

/**
 * Show info about repository
 */
public class InfoCommand extends AbstractCommand{
    Repository repository;

    public InfoCommand(Repository repository) {
        super("info", "Show info about current repository");
        this.repository = repository;
    }

    @Override
    public boolean execute(Object o) {  // Object o equals null by default
        ConsoleManager.replyUser(repository.toString());
        return true;
    }
}
