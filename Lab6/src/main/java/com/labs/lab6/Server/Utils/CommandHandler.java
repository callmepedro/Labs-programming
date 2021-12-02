package com.labs.lab6.Server.Utils;

import com.labs.lab6.Server.Commands.*;
import com.labs.lab6.common.AppObjects.SpaceMarine;
import com.labs.lab6.common.Request;

public class CommandHandler {

    private final CommandInvoker commandInvoker;

    public CommandHandler(Repository repository) {

        HelpCommand helpCommand = new HelpCommand();
        SaveCommand saveCommand = new SaveCommand(repository);
        ContainsCommand containsCommand = new ContainsCommand(repository);

        helpCommand.addToBlockingList(saveCommand);
        helpCommand.addToBlockingList(containsCommand);

        this.commandInvoker = new CommandInvoker(
                new AddCommand(repository),
                new UpdateCommand(repository),
                new RemoveByIdCommand(repository),
                new ClearCommand(repository),
                new InfoCommand(repository),
                new ShowCommand(repository),
                new RemoveAtCommand(repository),
                new RemoveLastCommand(repository),
                new RemoveLowerCommand(repository),
                new CountGreaterThanCategoryCommand(repository),
                new FilterLessThanLoyalCommand(repository),
                new PrintDescendingCommand(repository),
                new ExecuteScriptCommand(),
                new ExitCommand(saveCommand),
                saveCommand,
                containsCommand,
                helpCommand
        );
    }

    public void executeCommand(Request request){
        String commandName = request.getCommandStruct().getCommand();
        String msg;

        switch (commandName) {
            case "init":
                RepliesSendler.sendReply(String.valueOf(SpaceMarine.getCounter()));
            case "exit":
                if (commandInvoker.exit())
                    RepliesSendler.sendReply("Repository saved. Shutting-in client...");
                else {
                    RepliesSendler.sendReply("false");
                }
                break;

            case "contains":
                RepliesSendler.sendReply(Boolean.toString(commandInvoker.contains(request)));
                break;

            case "add":
                if (commandInvoker.add(request))
                    RepliesSendler.sendReply("Item added");
                else
                    RepliesSendler.sendReply("Item not added");
                break;

            case "info":
                if(commandInvoker.info()) {
                    msg = commandInvoker.getInfoCommand().getLastData();
                    RepliesSendler.sendReply(msg);
                }
                else RepliesSendler.sendReply("Something wrong...");
                break;

            case "show":
                if (commandInvoker.show()) {
                    msg = commandInvoker.getShowCommand().getLastData();
                    RepliesSendler.sendReply(msg);
                }
                else RepliesSendler.sendReply("Repository is empty");
                break;

            case "help":
                if (commandInvoker.help()) {
                    msg = commandInvoker.getHelpCommand().getLastData();
                    RepliesSendler.sendReply(msg);
                }
                else RepliesSendler.sendReply("Something wrong...");
                break;

            case "clear":
                if (commandInvoker.clear())
                    RepliesSendler.sendReply("Repository is cleaned up");
                else
                    RepliesSendler.sendReply("Repository is empty");
                break;

            case "print_descending":
                if (commandInvoker.printDescending()){
                    msg = commandInvoker.getPrintDescending().getLastData();
                    RepliesSendler.sendReply(msg);
                }
                else RepliesSendler.sendReply("Repository is empty");
                break;

            case "count_greater_than_category":
                if (commandInvoker.countGreaterThanCategory(request)){
                    msg = commandInvoker.getCountGreaterThanCategory().getLastData();
                    RepliesSendler.sendReply(msg);
                }
                else RepliesSendler.sendReply("There is no such elements");
                break;

            case "filter_less_than_loyal":
                if (commandInvoker.filterLessThanLoyal(request)) {
                    msg = commandInvoker.getFilterLessThanLoyal().getLastData();
                    RepliesSendler.sendReply(msg);
                }
                else RepliesSendler.sendReply("There is no such elements");
                break;

            case "remove_lower":
                if (commandInvoker.removeLower(request))
                    RepliesSendler.sendReply("Item removed");
                else
                    RepliesSendler.sendReply("Repository is empty");
                break;

            case "remove_last":
                if(commandInvoker.removeLast())
                    RepliesSendler.sendReply("Item removed");
                else
                    RepliesSendler.sendReply("Repository is empty");
                break;

            case "update":
                if (commandInvoker.update(request))
                    RepliesSendler.sendReply("Item updated");
                else
                    RepliesSendler.sendReply("No element with this ID");
                break;

            case "remove_by_id":
                if (commandInvoker.removeById(request))
                    RepliesSendler.sendReply("Item removed");
                else
                    RepliesSendler.sendReply("No element with this ID");
                break;

            case "remove_at":
                if (commandInvoker.removeAt(request))
                    RepliesSendler.sendReply("Item removed");
                else
                    RepliesSendler.sendReply("No element with this index");
                break;

        }

    }
}
