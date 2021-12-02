package com.labs.lab6.Server.Commands;

import com.labs.lab6.common.AppObjects.SpaceMarine;
import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

/**
 * Show all elements for which loyal less than given
 */
public class FilterLessThanLoyalCommand extends AbstractCommand{
    private final Repository repository;
    private final StringBuilder stringBuilder;
    private String lastData;

    public FilterLessThanLoyalCommand(Repository repository) {
        super("filter_less_than_loyal loyal", "Show all elements for which loyal less than given");
        this.repository = repository;
        this.stringBuilder = new StringBuilder();
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Request request) {
        boolean loyal = (boolean) request.getCommandArg();
        boolean isNotEmpty = false;
        String givenLoyalString = Boolean.toString(loyal);

        for (SpaceMarine elem : repository.getList()) {
            String elemLoyalString = Boolean.toString(elem.getLoyal());
            if (givenLoyalString.compareTo(elemLoyalString) > 0){
                stringBuilder.append(elem);
                isNotEmpty = true;
            }
        }
        lastData = stringBuilder.toString();
        return isNotEmpty;
    }
}
