package com.labs.lab5.AppUtils;

import com.labs.lab5.Commands.Command;
import com.labs.lab5.Commands.HelpCommand;

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
    private final Command helpCommand;

    public CommandInvoker(Command addCommand, Command updateCommand, Command removeByIdCommand, Command clearCommand,
                          Command infoCommand, Command showCommand, Command removeAtCommand, Command removeLast,
                          Command removeLower, Command countGreaterThanCategory, Command filterLessThanLoyal,
                          Command printDescending, Command executeScript, Command saveCommand, Command helpCommand) {
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
        this.helpCommand = helpCommand;
        this.saveCommand = saveCommand;
    }


    public boolean add() { return addCommand.execute(null); }

    public boolean update(Object o){ return updateCommand.execute(o); }

    public boolean removeById(Object o){ return removeByIdCommand.execute(o); }

    public boolean clear(){ return clearCommand.execute(null);}

    public boolean info() {return infoCommand.execute(null);}

    public boolean show() {return showCommand.execute(null);}

    public boolean removeAt(Object o) {return removeAtCommand.execute(o);}

    public boolean removeLast() {return removeLast.execute(null);}

    public boolean removeLower() {return removeLower.execute(null);}

    public boolean countGreaterThanCategory(Object o) {return countGreaterThanCategory.execute(o);}

    public boolean filterLessThanLoyal(Object o) {return filterLessThanLoyal.execute(o);}

    public boolean printDescending() { return printDescending.execute(null);}

    public boolean executeScript(Object o) { return executeScript.execute(o);}

    public boolean save() {return saveCommand.execute(null);}

    public boolean help() { return helpCommand.execute(this);}
}
