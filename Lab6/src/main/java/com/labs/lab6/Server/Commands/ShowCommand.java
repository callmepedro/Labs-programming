package com.labs.lab6.Server.Commands;

import com.labs.lab6.common.AppObjects.SpaceMarine;
import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

/**
 * Show information about elements of repository
 */
public class ShowCommand extends AbstractCommand{
    private final Repository repository;
    private String lastData;

    public ShowCommand(Repository repository) {
        super("show", "Show information about elements of repository");
        this.repository = repository;
        this.lastData = "";
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Request request) {  // response equals null by default
        StringBuilder description = new StringBuilder();

        if (repository.getList().isEmpty())
            return false;

        int counter = 0;
        for (SpaceMarine elem : repository.getList()){
            description.append(elem.toString());
            if (counter != repository.getList().size() - 1)
                description.append("\n");
            counter++;
        }
        lastData = description.toString();
        return true;
    }
}
