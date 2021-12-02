package com.labs.lab7.Server.Utils;

import com.labs.lab7.Server.Commands.*;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class calls execute methods on command objects
 */
public class CommandInvoker {

    private final Command addCommand;
    private final Command updateCommand;
    private final Command removeByIdCommand;
    private final Command clearCommand;
    private final Command infoCommand;
    private final Command showCommand;
    private final Command removeAtCommand;
    private final Command removeLast;
    private final Command removeLower;
    private final Command countGreaterThanCategory;
    private final Command filterLessThanLoyal;
    private final Command printDescending;
    private final Command executeScript;
    private final Command saveCommand;
    private final Command exitCommand;
    private final Command containsCommand;
    private final Command helpCommand;

    public Command getAddCommand() {
        return (AddCommand)addCommand;
    }

    public UpdateCommand getUpdateCommand() {
        return (UpdateCommand)updateCommand;
    }

    public RemoveByIdCommand getRemoveByIdCommand() {
        return (RemoveByIdCommand)removeByIdCommand;
    }

    public ClearCommand getClearCommand() {
        return (ClearCommand)clearCommand;
    }

    public InfoCommand getInfoCommand() {
        return (InfoCommand) infoCommand;
    }

    public ShowCommand getShowCommand() {
        return (ShowCommand)showCommand;
    }

    public RemoveAtCommand getRemoveAtCommand() {
        return (RemoveAtCommand) removeAtCommand;
    }

    public RemoveLastCommand getRemoveLast() {
        return (RemoveLastCommand) removeLast;
    }

    public RemoveLowerCommand getRemoveLower() {
        return (RemoveLowerCommand) removeLower;
    }

    public CountGreaterThanCategoryCommand getCountGreaterThanCategory() {
        return (CountGreaterThanCategoryCommand) countGreaterThanCategory;
    }

    public FilterLessThanLoyalCommand getFilterLessThanLoyal() {
        return (FilterLessThanLoyalCommand) filterLessThanLoyal;
    }

    public PrintDescendingCommand getPrintDescending() {
        return (PrintDescendingCommand) printDescending;
    }

    public ExecuteScriptCommand getExecuteScript() {
        return (ExecuteScriptCommand) executeScript;
    }

    public SaveCommand getSaveCommand() {
        return (SaveCommand) saveCommand;
    }

    public ContainsCommand getContainsCommand() {
        return (ContainsCommand) containsCommand;
    }

    public ExitCommand getExitCommand() {
        return (ExitCommand) exitCommand;
    }

    public HelpCommand getHelpCommand() {
        return (HelpCommand) helpCommand;
    }

    public CommandInvoker(Command addCommand, Command updateCommand, Command removeByIdCommand, Command clearCommand,
                          Command infoCommand, Command showCommand, Command removeAtCommand, Command removeLast,
                          Command removeLower, Command countGreaterThanCategory, Command filterLessThanLoyal,
                          Command printDescending, Command executeScript, Command exitCommand, Command saveCommand,
                          Command containsCommand, Command helpCommand) {
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.removeAtCommand = removeAtCommand;
        this.removeLast = removeLast;
        this.removeLower = removeLower;
        this.countGreaterThanCategory = countGreaterThanCategory;
        this.filterLessThanLoyal = filterLessThanLoyal;
        this.printDescending = printDescending;
        this.executeScript = executeScript;
        this.saveCommand = saveCommand;
        this.exitCommand = exitCommand;
        this.containsCommand = containsCommand;
        this.helpCommand = helpCommand;

    }


    public boolean add(Response o) { return addCommand.execute(o); }

    public boolean update(Response o){ return updateCommand.execute(o); }

    public boolean removeById(Response o){ return removeByIdCommand.execute(o); }

    public boolean clear(Response o){ return clearCommand.execute(o);}

    public boolean info() {return infoCommand.execute(null);}

    public boolean show() {return showCommand.execute(null);}

    public boolean removeAt(Response o) {return removeAtCommand.execute(o);}

    public boolean removeLast(Response o) {return removeLast.execute(o);}

    public boolean removeLower(Response o) {return removeLower.execute(o);}

    public boolean countGreaterThanCategory(Response o) {return countGreaterThanCategory.execute(o);}

    public boolean filterLessThanLoyal(Response o) {return filterLessThanLoyal.execute(o);}

    public boolean printDescending() {return printDescending.execute(null);}

    public boolean executeScript() {return executeScript.execute(null);}

    public boolean save() {return saveCommand.execute(null);}

    public boolean exit() {return exitCommand.execute(null);}

    public boolean contains(Response o) {return containsCommand.execute(o);}

    public boolean help() {return helpCommand.execute(new Response(null, null, this));}
}
