package com.labs.lab6.Server.Commands;

import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

/**
 * Show info about repository
 */
public class InfoCommand extends AbstractCommand{
    private final Repository repository;
    private String lastData;

    public InfoCommand(Repository repository) {
        super("info", "Show info about current repository");
        this.repository = repository;
        this.lastData = "";
    }

    public String getLastData(){
        return lastData;
    }

    @Override
    public boolean execute(Request request) {  // response equals null by default
        lastData = repository.toString();
        return true;
    }
}
