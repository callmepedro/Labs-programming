package com.labs.lab5.Commands;

import com.labs.lab5.AppUtils.Repository;

/**
 * Clear repository
 */
public class ClearCommand extends AbstractCommand{
    Repository repository;

    public ClearCommand(Repository repository) {
        super("clear", "Clear repository");
        this.repository = repository;
    }

    @Override
    public boolean execute(Object o){   // Object o equals null by default
        if (repository.getList().isEmpty())
            return false;
        repository.getList().clear();
        return true;
    }
}
