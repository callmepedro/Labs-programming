package com.labs.lab6.Server.Commands;

import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

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
    public boolean execute(Request request){
        int id = Integer.parseInt(request.getCommandStruct().getArgument());
        return repository.getList().removeIf(n -> n.getId() == id);
    }
}
