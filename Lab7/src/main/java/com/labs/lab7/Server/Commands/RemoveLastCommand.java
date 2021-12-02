package com.labs.lab7.Server.Commands;

import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Remove last element from repository
 */
public class RemoveLastCommand extends AbstractCommand{
    private final Repository repository;
    private final ReadWriteLock lock;
    private boolean isEmpty;

    public RemoveLastCommand(Repository repository) {
        super("remove_last", "Remove last element from collection");
        this.repository = repository;
        lock = new ReentrantReadWriteLock();
    }

    public boolean getLastData() {
        return isEmpty;
    }

    @Override
    public boolean execute(Response response) {  // response equals null by default
        lock.writeLock().lock();
        try {
            int index = repository.getList().size() - 1;
            if (index < 0) {
                isEmpty = true;
                return false;
            }
            isEmpty = false;

            String login = repository.getList().get(index).getInitData().getLogin();
            if (!login.equals(response.getInitData().getLogin())) {
                return false;
            }
            repository.getList().remove(index);
            return true;

        } catch (Throwable e){
            System.out.println("RemoveLastCommand: " + e.getMessage());
            return false;

        } finally {
            lock.writeLock().unlock();
        }
    }
}
