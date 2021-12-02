package com.labs.lab5.Commands;

import com.labs.lab5.AppObjects.SpaceMarine;
import com.labs.lab5.AppUtils.MarineCreator;
import com.labs.lab5.AppUtils.Repository;

/**
 * Add new space marine to repository
 */
public class AddCommand extends AbstractCommand {
    private Repository repository;
    private MarineCreator marineCreator;
    private SpaceMarine spaceMarine;

    public AddCommand(Repository repository, MarineCreator marineCreator) {
        super("add {element}", "Add new element to repository");
        this.repository = repository;
        this.marineCreator = marineCreator;
    }

    @Override
    public boolean execute(Object o) {  // Object o equals null by default
        spaceMarine = marineCreator.create();
        if (spaceMarine == null)
            return false;
        return repository.getList().add(spaceMarine);
    }
}
