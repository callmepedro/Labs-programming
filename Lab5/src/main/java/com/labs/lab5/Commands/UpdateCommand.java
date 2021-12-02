package com.labs.lab5.Commands;

import com.labs.lab5.AppObjects.SpaceMarine;
import com.labs.lab5.AppUtils.ConsoleManager;
import com.labs.lab5.AppUtils.MarineCreator;
import com.labs.lab5.AppUtils.Repository;

/**
 * Update element of repository by ID
 */
public class UpdateCommand extends AbstractCommand {
    private Repository repository;
    private MarineCreator marineCreator;

    public UpdateCommand(Repository repository, MarineCreator marineCreator) {
        super("update id {element}", "Update value of Repository's element by ID");
        this.repository = repository;
        this.marineCreator = marineCreator;
    }

    @Override
    public boolean execute(Object o) {
        int id = (int)o;
        int prevCounter = SpaceMarine.getCounter();
        SpaceMarine.setCounter(id-1);

        for (int i = 0; i < repository.size(); ++i) {
            if (repository.getList().get(i).getId() == id){
                repository.getList().set(i, marineCreator.create());
                SpaceMarine.setCounter(prevCounter);
                return true;
            }
        }
        SpaceMarine.setCounter(prevCounter);
        return false;
    }
}