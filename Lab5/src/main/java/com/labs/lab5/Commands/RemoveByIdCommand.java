package com.labs.lab5.Commands;

import com.labs.lab5.AppUtils.ConsoleManager;
import com.labs.lab5.AppUtils.Repository;

/**
 * Remove element of repository by ID
 */
public class RemoveByIdCommand extends AbstractCommand {
    private Repository repository;

    public RemoveByIdCommand(Repository repository){
        super("remove_by_id id", "Remove element from repository by ID");
        this.repository = repository;
    }

    @Override
    public boolean execute(Object o){
        int id = (int)o;
        return repository.getList().removeIf(n -> n.getId() == id);
    }
}
