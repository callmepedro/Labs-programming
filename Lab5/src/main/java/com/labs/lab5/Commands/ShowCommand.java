package com.labs.lab5.Commands;

import com.labs.lab5.AppObjects.SpaceMarine;
import com.labs.lab5.AppUtils.ConsoleManager;
import com.labs.lab5.AppUtils.Repository;

/**
 * Show information about elements of repository
 */
public class ShowCommand extends AbstractCommand{
    Repository repository;

    public ShowCommand(Repository repository) {
        super("show", "Show information about elements of repository");
        this.repository = repository;
    }

    @Override
    public boolean execute(Object o) {  // Object o equals null by default
        StringBuilder description = new StringBuilder();
        int counter = 0;
        for (SpaceMarine elem : repository.getList()){
            description.append(elem.toString());
            if (counter != repository.getList().size() - 1)
                description.append("\n");
            counter++;
        }
        ConsoleManager.replyUser(description.toString());
        return true;
    }
}
