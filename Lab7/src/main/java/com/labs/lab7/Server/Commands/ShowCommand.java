package com.labs.lab7.Server.Commands;

import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Show information about elements of repository
 */
public class ShowCommand extends AbstractCommand{
    private final Repository repository;
    private String lastData;
    private final ReadWriteLock lock;

    public ShowCommand(Repository repository) {
        super("show", "Show information about elements of repository");
        this.repository = repository;
        this.lastData = "";
        this.lock = new ReentrantReadWriteLock();
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Response response) {  // response equals null by default
        StringBuilder description = new StringBuilder();

        lock.readLock().lock();
        try {
            if (repository.getList().isEmpty())
                return false;

            int counter = 0;
            for (SpaceMarine elem : repository.getList()) {
                description.append(elem.toString());
                if (counter != repository.getList().size() - 1)
                    description.append("\n\n");
                counter++;
            }
            lastData = description.toString();
            return true;

        } finally {
            lock.readLock().unlock();
        }
    }
}
