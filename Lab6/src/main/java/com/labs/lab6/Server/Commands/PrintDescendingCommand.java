package com.labs.lab6.Server.Commands;

import com.labs.lab6.common.AppObjects.SpaceMarine;
import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Print elements of repository in descending order
 */
public class PrintDescendingCommand extends AbstractCommand {
    private final Repository repository;
    private String lastData;

    public PrintDescendingCommand(Repository repository) {
        super("print_descending", "Print elements of repository in descending order");
        this.repository = repository;
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Request request) {  // response equals null by default
        if (repository.getList().isEmpty()) return false;
        List<SpaceMarine> sortedRepository = new ArrayList<>(repository.getList());
        sortedRepository.sort(Comparator.comparingInt(SpaceMarine::getId));

        StringBuilder description = new StringBuilder();
        for (SpaceMarine elem : sortedRepository){
            description.append(elem.toString()).append("\n");
        }
        lastData = description.toString();
        return true;
    }
}
