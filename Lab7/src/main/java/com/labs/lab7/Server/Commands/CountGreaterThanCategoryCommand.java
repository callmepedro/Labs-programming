package com.labs.lab7.Server.Commands;

import com.labs.lab7.common.AppObjects.AstartesCategory;
import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Counts the number of elements for which category greater than given
 */
public class CountGreaterThanCategoryCommand extends AbstractCommand{
    private final Repository repository;
    private String lastData;
    private final ReadWriteLock lock;

    public CountGreaterThanCategoryCommand(Repository repository){
        super("count_greater_than_category category", "Counts the number of elements for which category greater than given");
        this.repository = repository;
        this.lock = new ReentrantReadWriteLock();
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Response response) {

        String iterCategory;
        String givenCategory;
        AstartesCategory category = (AstartesCategory)response.getCommandArg();
        int counter = 0;

        lock.readLock().lock();
        try {
            for (SpaceMarine elem : repository.getList()) {
                iterCategory = elem.getCategory().toString();
                givenCategory = category.toString();
                if (iterCategory.compareTo(givenCategory) > 0) {
                    counter++;
                }
            }
            if (counter > 0) {
                lastData = counter + " elements have category greater than given";
                return true;
            }
            return false;

        } catch (Throwable e){
            System.out.println("CountGreaterThanCategoryCommand: " + e.getMessage());
            return false;

        } finally {
            lock.readLock().unlock();
        }
    }
}
