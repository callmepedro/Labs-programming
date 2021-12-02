package com.labs.lab7.Server.Commands;

import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Remove element of repository by ID
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final Repository repository;
    private final ReadWriteLock lock;

    public RemoveByIdCommand(Repository repository){
        super("remove_by_id id", "Remove element from repository by ID");
        this.repository = repository;
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public boolean execute(Response response){
        int id = Integer.parseInt(response.getCommandStruct().getArgument());

        lock.writeLock().lock();
        try {
            return repository.getList().removeIf(n -> n.getId() == id &&
                    n.getInitData().getLogin().equals(response.getInitData().getLogin()));

        } catch (Throwable e) {
            System.out.println("RemoveByIdCommand: " + e.getMessage());
            return false;

        } finally {
            lock.writeLock().unlock();
        }
    }
}
