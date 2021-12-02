package com.labs.lab6.Server.Commands;

import com.labs.lab6.common.Request;

/**
 * Execute script from text file
 */
public class ExecuteScriptCommand extends AbstractCommand {

    public ExecuteScriptCommand(){
        super("execute_script file_name", "Execute script from file");
    }

    @Override
    public boolean execute(Request request) {

        return true;
    }
}
