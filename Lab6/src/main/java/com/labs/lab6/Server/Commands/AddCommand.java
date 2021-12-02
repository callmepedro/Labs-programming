package com.labs.lab6.Server.Commands;

import com.labs.lab6.common.AppObjects.SpaceMarine;
import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

/**
 * Add new space marine to repository
 */
public class AddCommand extends AbstractCommand {
    private Repository repository;
    private SpaceMarine spaceMarine;

    public AddCommand(Repository repository) {
        super("add {element}", "Add new element to repository");
        this.repository = repository;
    }

    @Override
    public boolean execute(Request request) {  // Object o equals null by default
        spaceMarine = (SpaceMarine) request.getCommandArg();
        return repository.getList().add(spaceMarine);
    }
}
