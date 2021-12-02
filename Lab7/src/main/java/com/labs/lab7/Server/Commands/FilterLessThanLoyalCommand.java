package com.labs.lab7.Server.Commands;

import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Show all elements for which loyal less than given
 */
public class FilterLessThanLoyalCommand extends AbstractCommand{
    private final Repository repository;
    private final StringBuilder stringBuilder;
    private String lastData;
    private final ReadWriteLock lock;

    public FilterLessThanLoyalCommand(Repository repository) {
        super("filter_less_than_loyal loyal", "Show all elements for which loyal less than given");
        this.repository = repository;
        this.stringBuilder = new StringBuilder();
        this.lock = new ReentrantReadWriteLock();
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Response response) {
        boolean loyal = (boolean)response.getCommandArg();
        boolean isNotEmpty = false;
        String givenLoyalString = Boolean.toString(loyal);

        lock.readLock().lock();
        try {
            for (SpaceMarine elem : repository.getList()) {
                String elemLoyalString = Boolean.toString(elem.getLoyal());
                if (givenLoyalString.compareTo(elemLoyalString) > 0) {
                    stringBuilder.append(elem);
                    isNotEmpty = true;
                }
            }
            lastData = stringBuilder.toString();
            return isNotEmpty;

        } catch (Throwable e){
            System.out.println("FilterLessThanLoyalCommand: " + e.getMessage());
            return false;

        } finally {
            lock.readLock().unlock();
        }
    }
}
