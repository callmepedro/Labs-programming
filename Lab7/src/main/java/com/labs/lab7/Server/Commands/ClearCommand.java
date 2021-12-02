package com.labs.lab7.Server.Commands;

import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Clear repository
 */
public class ClearCommand extends AbstractCommand{
    private final Repository repository;
    private final ReadWriteLock lock;
    private int itemsRemoved;

    public ClearCommand(Repository repository) {
        super("clear", "Clear repository");
        this.repository = repository;
        this.lock = new ReentrantReadWriteLock();
    }

    public int getLastData() {
        return itemsRemoved;
    }

    @Override
    public boolean execute(Response response){
        lock.writeLock().lock();
        try {
            int sizeBefore = repository.size();
            boolean state = repository.getList().removeIf(
                    n -> n.getInitData().getLogin().equals(response.getInitData().getLogin()));
            int sizeAfter = repository.size();

            itemsRemoved = sizeBefore - sizeAfter;
            return state;

        } catch (Throwable e) {
            System.out.println("ClearCommand: " + e.getMessage());
            return false;

        } finally {
            lock.writeLock().unlock();
        }
    }
}
