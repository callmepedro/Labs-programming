package com.labs.lab6.Server.Commands;

import com.labs.lab6.common.AppObjects.SpaceMarine;
import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

/**
 * Remove all elements with less health than given
 */
public class RemoveLowerCommand extends AbstractCommand{
    Repository repository;
    SpaceMarine spaceMarine;

    public RemoveLowerCommand(Repository repository) {
        super("remove_lower {element}", "Remove all elements lower than given");
        this.repository = repository;

    }
    @Override
    public boolean execute(Request request) {
        spaceMarine = (SpaceMarine) request.getCommandArg();
        return repository.getList().removeIf(n -> n.compareTo(spaceMarine) < 0);
    }
}
