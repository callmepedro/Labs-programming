package com.labs.lab7.Server.Utils;

import com.labs.lab7.Server.App.Main;
import com.labs.lab7.Server.Commands.*;
import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.common.Response;

import java.util.HashMap;

public class CommandHandler {

    private final CommandInvoker commandInvoker;
    private final RepliesSendler repliesSendler;
    private final UserIdentification userIdentification;
    private final Repository repository;
    private final DatabaseManager databaseManager;

    public CommandHandler(Repository repository, RepliesSendler repliesSendler,
                          UserIdentification userIdentification, DatabaseManager databaseManager) {

        this.repliesSendler = repliesSendler;
        this.userIdentification = userIdentification;
        this.repository = repository;
        this.databaseManager = databaseManager;
        
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

    public void executeCommand(Response response){
        String commandName = response.getCommandStruct().getCommand();
        assert commandName != null;

        String msg, login, password;

        login = response.getInitData().getLogin();
        password = response.getInitData().getPassword();

        if(!commandName.equals("auth") &&
                !commandName.equals("register") &&
                !commandName.equals("init") &&
                !userIdentification.authUser(login, password)){
            repliesSendler.sendReply("User is not registered/authorized");
        }

        switch (commandName) {
            case "init":
                login = response.getInitData().getLogin();
                password = response.getInitData().getPassword();
                if (userIdentification.authUser(login, password)) {
                    repliesSendler.sendReply(String.valueOf(SpaceMarine.getCounter()));
                    break;
                }
                repliesSendler.sendReply("User is not registered/authorized");
                break;

            case "auth":
                login = response.getInitData().getLogin();
                password = response.getInitData().getPassword();
                repliesSendler.sendReply(Boolean.toString(userIdentification.authUser(login, password)));
                break;

            case "register":
                login = response.getInitData().getLogin();
                password = response.getInitData().getPassword();
                repliesSendler.sendReply(Boolean.toString(userIdentification.regUser(login, password)));
                break;

            case "exit":
                //userIdentification.saveUsersDataToXML(Main.getUserDataFileName());
                if (commandInvoker.exit()) {
                    repliesSendler.sendReply("Repository saved. Shutting-in client...");
                    break;
                }
                repliesSendler.sendReply("false");
                break;

            case "contains":
                if (commandInvoker.contains(response)){
                    repliesSendler.sendReply("true");
                    break;
                }
                msg = commandInvoker.getContainsCommand().getLastData();
                assert msg != null;
                repliesSendler.sendReply(msg);
                break;

            case "add":
                if (commandInvoker.add(response)) {
                    repliesSendler.sendReply("Item added");
                    updateDatabase();
                    break;
                }
                repliesSendler.sendReply("Item not added");
                break;

            case "info":
                if(commandInvoker.info()) {
                    msg = commandInvoker.getInfoCommand().getLastData();
                    repliesSendler.sendReply(msg);
                    break;
                }
                repliesSendler.sendReply("Something wrong...");
                break;

            case "show":
                if (commandInvoker.show()) {
                    msg = commandInvoker.getShowCommand().getLastData();
                    repliesSendler.sendReply(msg);
                    break;
                }
                repliesSendler.sendReply("Repository is empty");
                break;

            case "help":
                if (commandInvoker.help()) {
                    msg = commandInvoker.getHelpCommand().getLastData();
                    repliesSendler.sendReply(msg);
                    break;
                }
                repliesSendler.sendReply("Something wrong...");
                break;

            case "clear":
                if (commandInvoker.clear(response)) {
                    int cnt = commandInvoker.getClearCommand().getLastData();
                    repliesSendler.sendReply("Removed all items owned by this user (" + cnt + ")");
                    updateDatabase();
                    break;
                }
                repliesSendler.sendReply("There are no current user items here");
                break;

            case "print_descending":
                if (commandInvoker.printDescending()){
                    msg = commandInvoker.getPrintDescending().getLastData();
                    repliesSendler.sendReply(msg);
                    break;
                }
                repliesSendler.sendReply("Repository is empty");
                break;

            case "count_greater_than_category":
                if (commandInvoker.countGreaterThanCategory(response)){
                    msg = commandInvoker.getCountGreaterThanCategory().getLastData();
                    repliesSendler.sendReply(msg);
                    break;
                }
                repliesSendler.sendReply("There are no such elements here");
                break;

            case "filter_less_than_loyal":
                if (commandInvoker.filterLessThanLoyal(response)) {
                    msg = commandInvoker.getFilterLessThanLoyal().getLastData();
                    repliesSendler.sendReply(msg);
                    break;
                }
                repliesSendler.sendReply("There are no such elements here");
                break;

            case "remove_lower":
                if (commandInvoker.removeLower(response)) {
                    int cnt = commandInvoker.getRemoveLower().getLastData();
                    repliesSendler.sendReply("Items removed successfully (" + cnt + ")");
                    updateDatabase();
                    break;
                }
                repliesSendler.sendReply("There are no such elements here");
                break;

            case "remove_last":
                if(commandInvoker.removeLast(response)) {
                    repliesSendler.sendReply("Item removed");
                    updateDatabase();
                    break;
                }
                boolean isEmpty = commandInvoker.getRemoveLast().getLastData();
                if (isEmpty) {
                    repliesSendler.sendReply("Repository is empty");
                    break;
                }
                repliesSendler.sendReply("Current user have no access to the last element");
                break;

            case "update":
                if (commandInvoker.update(response)) {
                    repliesSendler.sendReply("Item updated");
                    updateDatabase();
                    break;
                }
                repliesSendler.sendReply("There are no current user's item with this ID here");
                break;

            case "remove_by_id":
                if (commandInvoker.removeById(response)) {
                    repliesSendler.sendReply("Item removed");
                    updateDatabase();
                    break;
                }
                repliesSendler.sendReply("No current user's element with this ID");
                break;

            case "remove_at":
                if (commandInvoker.removeAt(response)) {
                    repliesSendler.sendReply("Item removed");
                    updateDatabase();
                    break;
                }
                repliesSendler.sendReply("No current user's element with this index");
                break;
        }
    }
    synchronized private void updateDatabase(){
        databaseManager.clear();
        for (int i = 0; i < repository.size(); ++i){
            SpaceMarine spaceMarine = repository.getList().get(i);
            databaseManager.upload(spaceMarine);
        }
    }
}
