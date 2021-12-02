package com.labs.lab6.Server.Commands;

import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.AppObjects.SpaceMarine;
import com.labs.lab6.common.Request;

public class ContainsCommand extends AbstractCommand{
    private final Repository repository;

    public ContainsCommand(Repository repository) {
        super("contains", "check if repository contains element with such id");
        this.repository = repository;
    }

    @Override
    public boolean execute(Request request) {
        int id = Integer.parseInt(request.getCommandStruct().getArgument());

        for (SpaceMarine elem : repository.getList()) {
            if (elem.getId() == id) {
                return true;
            }
        }

        return false;
    }
}
