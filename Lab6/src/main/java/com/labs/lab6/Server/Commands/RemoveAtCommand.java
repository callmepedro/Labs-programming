package com.labs.lab6.Server.Commands;

import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

/**
 * Remove the element of repository by index
 */
public class RemoveAtCommand extends AbstractCommand{
    Repository repository;

    public RemoveAtCommand(Repository repository) {
        super("remove_at index", "Remove element by index");
        this.repository = repository;
    }
    @Override
    public boolean execute(Request request) {
        int index = Integer.parseInt(request.getCommandStruct().getArgument());
        if (repository.getList().size() < index || index < 1)
            return false;
        repository.getList().remove(index-1);
        return true;
    }
}
