package com.labs.lab5.Commands;

import com.labs.lab5.AppUtils.ConsoleManager;
import com.labs.lab5.AppUtils.Repository;

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
    public boolean execute(Object o) {
        int index = (int)o;
        if (repository.getList().size() < index || index < 1)
            return false;
        repository.getList().remove(index-1);
        return true;
    }
}
