package com.labs.lab7.Server.Commands;

import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Remove the element of repository by index
 */
public class RemoveAtCommand extends AbstractCommand{
    private final Repository repository;
    private final ReadWriteLock lock;

    public RemoveAtCommand(Repository repository) {
        super("remove_at index", "Remove element by index");
        this.repository = repository;
        this.lock = new ReentrantReadWriteLock();
    }
    @Override
    public boolean execute(Response response) {

        lock.writeLock().lock();
        try {
            int index = Integer.parseInt(response.getCommandStruct().getArgument());

            if (repository.getList().size() < index || index < 1)
                return false;

            String login = repository.getList().get(index - 1).getInitData().getLogin();
            if (!login.equals(response.getInitData().getLogin()))
                return false;

            repository.getList().remove(index - 1);
            return true;

        } catch (Throwable e) {
            System.out.println("RemoveAtCommand: " + e.getMessage());
            return false;

        } finally {
            lock.writeLock().unlock();
        }
    }
}
