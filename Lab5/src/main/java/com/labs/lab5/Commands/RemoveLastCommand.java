package com.labs.lab5.Commands;

import com.labs.lab5.AppUtils.Repository;

/**
 * Remove last element from repository
 */
public class RemoveLastCommand extends AbstractCommand{
    Repository repository;

    public RemoveLastCommand(Repository repository) {
        super("remove_last", "Remove last element from collection");
        this.repository = repository;
    }

    @Override
    public boolean execute(Object o) {  // Object o equals null by default
        int index = repository.getList().size() - 1;
        if (index < 0)
            return false;
        repository.getList().remove(index);
        return true;
    }
}
