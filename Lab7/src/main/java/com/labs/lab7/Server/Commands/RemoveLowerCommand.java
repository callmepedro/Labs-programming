package com.labs.lab7.Server.Commands;

import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Remove all elements with less health than given
 */
public class RemoveLowerCommand extends AbstractCommand{
    private final Repository repository;
    private SpaceMarine spaceMarine;
    private final ReadWriteLock lock;
    private int itemsRemoved;

    public RemoveLowerCommand(Repository repository) {
        super("remove_lower {element}", "Remove all elements lower than given");
        this.repository = repository;
        this.lock = new ReentrantReadWriteLock();
    }

    public int getLastData(){
        return itemsRemoved;
    }

    @Override
    public boolean execute(Response response) {
        spaceMarine = (SpaceMarine) response.getCommandArg();
        String login = response.getInitData().getLogin();

        lock.writeLock().lock();
        try {
            int sizeBefore = repository.size();
            boolean state = repository.getList().removeIf(n -> n.compareTo(spaceMarine) < 0 &&
                    n.getInitData().getLogin().equals(login));
            int sizeAfter = repository.size();

            itemsRemoved = sizeBefore - sizeAfter;
            return state;

        } catch (Throwable e){
            System.out.println("RemoveLowerCommand: " + e.getMessage());
            return false;

        } finally {
            lock.writeLock().unlock();
        }
    }
}
