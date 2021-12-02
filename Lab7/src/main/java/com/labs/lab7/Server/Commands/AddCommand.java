package com.labs.lab7.Server.Commands;

import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Add new space marine to repository
 */
public class AddCommand extends AbstractCommand {
    private final Repository repository;
    private final ReadWriteLock lock;

    public AddCommand(Repository repository) {
        super("add {element}", "Add new element to repository");
        this.repository = repository;
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public boolean execute(Response response) {  // Object o equals null by default
        lock.writeLock().lock();
        try {
            SpaceMarine spaceMarine = (SpaceMarine) response.getCommandArg();
            boolean state = repository.getList().add(spaceMarine);


            return state;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
