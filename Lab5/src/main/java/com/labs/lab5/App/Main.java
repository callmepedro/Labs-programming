package com.labs.lab5.App;


import com.labs.lab5.AppUtils.*;
import com.labs.lab5.Commands.*;


/**
 * Main application class.
 * @author Petr Soloviev
 */
public class Main {

    public static void main(String[] args) {

        RepositoryBuilder repositoryBuilder = new RepositoryBuilder();
        Repository repository = repositoryBuilder.buildFromXml();
        MarineCreator marineCreator = new MarineCreator();

        CommandInvoker commandInvoker = new CommandInvoker(
                new AddCommand(repository, marineCreator),
                new UpdateCommand(repository, marineCreator),
                new RemoveByIdCommand(repository),
                new ClearCommand(repository),
                new InfoCommand(repository),
                new ShowCommand(repository),
                new RemoveAtCommand(repository),
                new RemoveLastCommand(repository),
                new RemoveLowerCommand(repository, marineCreator),
                new CountGreaterThanCategoryCommand(repository),
                new FilterLessThanLoyalCommand(repository),
                new PrintDescendingCommand(repository),
                new ExecuteScriptCommand(),
                new SaveCommand(repository),
                new HelpCommand()
        );

        ConsoleManager consoleManager = new ConsoleManager(commandInvoker);

        consoleManager.run();
    }

    private static final String envFileName = "FILENAME_VAR";

    public static String getFileName() {
        return System.getenv(envFileName);
    }
}
