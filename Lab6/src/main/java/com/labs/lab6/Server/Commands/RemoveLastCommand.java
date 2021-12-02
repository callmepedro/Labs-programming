package com.labs.lab6.Server.Commands;

import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

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
    public boolean execute(Request request) {  // response equals null by default
        int index = repository.getList().size() - 1;
        if (index < 0)
            return false;
        repository.getList().remove(index);
        return true;
    }
}
