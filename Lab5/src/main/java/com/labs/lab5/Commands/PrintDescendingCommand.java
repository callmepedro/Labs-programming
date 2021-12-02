package com.labs.lab5.Commands;

import com.labs.lab5.AppObjects.SpaceMarine;
import com.labs.lab5.AppUtils.ConsoleManager;
import com.labs.lab5.AppUtils.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Print elements of repository in descending order
 */
public class PrintDescendingCommand extends AbstractCommand {
    Repository repository;

    public PrintDescendingCommand(Repository repository) {
        super("print_descending", "Print elements of repository in descending order");
        this.repository = repository;
    }
    @Override
    public boolean execute(Object o) {  // Object o equals null by default
        if (repository.getList().isEmpty()) return false;
        List<SpaceMarine> sortedRepository = new ArrayList<>(repository.getList());
        sortedRepository.sort(Comparator.comparingInt(SpaceMarine::getId));

        StringBuilder description = new StringBuilder();
        for (SpaceMarine elem : sortedRepository){
            description.append(elem.toString()).append("\n");
        }
        ConsoleManager.replyUser(description.toString());
        return true;
    }
}
