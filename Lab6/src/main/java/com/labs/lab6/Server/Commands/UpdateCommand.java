package com.labs.lab6.Server.Commands;

import com.labs.lab6.common.AppObjects.SpaceMarine;
import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

/**
 * Update element of repository by ID
 */
public class UpdateCommand extends AbstractCommand {
    private final Repository repository;

    public UpdateCommand(Repository repository) {
        super("update id {element}", "Update value of Repository's element by ID");
        this.repository = repository;
    }

    @Override
    public boolean execute(Request request) {
        SpaceMarine spaceMarine = (SpaceMarine) request.getCommandArg();

        int id = Integer.parseInt(request.getCommandStruct().getArgument());
        spaceMarine.setId(id);

        for (int i = 0; i < repository.size(); ++i) {
            if (repository.getList().get(i).getId() == id){
                repository.getList().set(i, spaceMarine);
                return true;
            }
        }
        return false;
    }
}