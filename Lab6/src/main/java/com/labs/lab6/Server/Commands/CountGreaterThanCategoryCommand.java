package com.labs.lab6.Server.Commands;

import com.labs.lab6.common.AppObjects.AstartesCategory;
import com.labs.lab6.common.AppObjects.SpaceMarine;
import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;

/**
 * Counts the number of elements for which category greater than given
 */
public class CountGreaterThanCategoryCommand extends AbstractCommand{
    private final Repository repository;
    private String lastData;

    public CountGreaterThanCategoryCommand(Repository repository){
        super("count_greater_than_category category", "Counts the number of elements for which category greater than given");
        this.repository = repository;
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Request request) {

        String iterCategory;
        String givenCategory;
        AstartesCategory category = (AstartesCategory) request.getCommandArg();
        int counter = 0;
        for (SpaceMarine elem : repository.getList()) {
            iterCategory = elem.getCategory().toString();
            givenCategory = category.toString();
            if (iterCategory.compareTo(givenCategory) > 0){
                counter++;
            }
        }
        if (counter > 0) {
            lastData = counter + " elements have category greater than given";
            return true;
        }
        return false;
    }
}
