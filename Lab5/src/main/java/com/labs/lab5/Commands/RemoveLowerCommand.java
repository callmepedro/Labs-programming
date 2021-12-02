package com.labs.lab5.Commands;

import com.labs.lab5.AppUtils.MarineCreator;
import com.labs.lab5.AppUtils.Repository;

/**
 * Remove all elements with less health than given
 */
public class RemoveLowerCommand extends AbstractCommand{
    Repository repository;
    MarineCreator marineCreator;

    public RemoveLowerCommand(Repository repository, MarineCreator marineCreator) {
        super("remove_lower {element}", "Remove all elements lower than given");
        this.repository = repository;
        this.marineCreator = marineCreator;
    }
    @Override
    public boolean execute(Object o) {  // Object o equals null by default
        return repository.getList().removeIf(n -> n.compareTo(marineCreator.create()) < 0);
    }
}
