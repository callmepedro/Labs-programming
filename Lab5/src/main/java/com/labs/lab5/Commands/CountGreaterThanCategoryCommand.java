package com.labs.lab5.Commands;

import com.labs.lab5.AppObjects.AstartesCategory;
import com.labs.lab5.AppObjects.SpaceMarine;
import com.labs.lab5.AppUtils.ConsoleManager;
import com.labs.lab5.AppUtils.Repository;

/**
 * Counts the number of elements for which category greater than given
 */
public class CountGreaterThanCategoryCommand extends AbstractCommand{
    Repository repository;
    public CountGreaterThanCategoryCommand(Repository repository){
        super("count_greater_than_category category", "Counts the number of elements for which category greater than given");
        this.repository = repository;
    }

    @Override
    public boolean execute(Object o) {
        String iterCategory;
        String givenCategory;
        AstartesCategory category = (AstartesCategory)o;
        int counter = 0;
        for (SpaceMarine elem : repository.getList()) {
            iterCategory = elem.getCategory().toString();
            givenCategory = category.toString();
            if (iterCategory.compareTo(givenCategory) > 0){
                counter++;
            }
        }
        if (counter > 0) {
            ConsoleManager.replyUser(counter + " elements have category greater than given");
            return true;
        }
        return false;
    }
}
