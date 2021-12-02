package com.labs.lab7.Server.Commands;

import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Print elements of repository in descending order
 */
public class PrintDescendingCommand extends AbstractCommand {
    private final Repository repository;
    private String lastData;
    private final ReadWriteLock lock;

    public PrintDescendingCommand(Repository repository) {
        super("print_descending", "Print elements of repository in descending order");
        this.repository = repository;
        this.lock = new ReentrantReadWriteLock();
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Response response) {  // response equals null by default
        lock.readLock().lock();
        try {
            if (repository.getList().isEmpty()) return false;
            List<SpaceMarine> sortedRepository = new ArrayList<>(repository.getList());
            sortedRepository.sort(Comparator.comparingInt(SpaceMarine::getId));

            StringBuilder description = new StringBuilder();
            for (SpaceMarine elem : sortedRepository) {
                description.append(elem.toString()).append("\n");
            }
            lastData = description.toString();
            return true;

        } catch (Throwable e){
            System.out.println("PrintDescendingCommand: " + e.getMessage());
            return false;

        } finally {
            lock.readLock().unlock();
        }
    }
}
