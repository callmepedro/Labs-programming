package com.labs.lab5.Commands;

import com.labs.lab5.AppObjects.SpaceMarine;
import com.labs.lab5.AppUtils.ConsoleManager;
import com.labs.lab5.AppUtils.Repository;

/**
 * Show all elements for which loyal less than given
 */
public class FilterLessThanLoyalCommand extends AbstractCommand{
    Repository repository;

    public FilterLessThanLoyalCommand(Repository repository) {
        super("filter_less_than_loyal loyal", "Show all elements for which loyal less than given");
        this.repository = repository;
    }
    @Override
    public boolean execute(Object o) {
        boolean loyal = (boolean)o;
        boolean isNotEmpty = false;
        String givenLoyalString = Boolean.toString(loyal);

        for (SpaceMarine elem : repository.getList()) {
            String elemLoyalString = Boolean.toString(elem.getLoyal());
            if (givenLoyalString.compareTo(elemLoyalString) > 0){
                ConsoleManager.replyUser(elem.toString());
                isNotEmpty = true;
            }
        }
        return isNotEmpty;
    }
}
