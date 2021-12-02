package com.labs.lab7.Server.Commands;

import com.labs.lab7.common.Response;

/**
 * Execute script from text file
 */
public class ExecuteScriptCommand extends AbstractCommand {

    public ExecuteScriptCommand(){
        super("execute_script file_name", "Execute script from file");
    }

    @Override
    public boolean execute(Response response) {
        return true;
    }
}
