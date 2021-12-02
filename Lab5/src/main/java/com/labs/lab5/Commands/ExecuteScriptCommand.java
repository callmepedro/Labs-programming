package com.labs.lab5.Commands;


import com.labs.lab5.AppUtils.CommandReaderMode;
import com.labs.lab5.AppUtils.ConsoleManager;
import com.labs.lab5.Exceptions.ExecuteScriptRecursionException;

import java.io.FileNotFoundException;
import java.util.HashSet;

/**
 * Execute script from text file
 */
public class ExecuteScriptCommand extends AbstractCommand {

    private HashSet<String> executeScriptCommandsPull = new HashSet<>();

    public ExecuteScriptCommand(){
        super("execute_script file_name", "Execute script from file");
    }

    @Override
    public boolean execute(Object o) {
        String path = (String)o;
        try {
            if (ConsoleManager.getCommandReader().getCommandReaderMod() == CommandReaderMode.CONSOLE) {
                executeScriptCommandsPull.clear();
            }
            else if (executeScriptCommandsPull.contains(path)) {
                throw new ExecuteScriptRecursionException("This command causes infinite recursion");
            }
            else {
                executeScriptCommandsPull.add(path);
            }
            ConsoleManager.getCommandReader().setFileMod(path);

        } catch (ExecuteScriptRecursionException e){
            ConsoleManager.replyUser(e.getMessage());
            return false;

        } catch (FileNotFoundException e) {
            ConsoleManager.replyUser("File not found");
            return false;
        }
        return true;
    }
}
