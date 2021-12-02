package com.labs.lab7.Server.Commands;

import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Show info about repository
 */
public class InfoCommand extends AbstractCommand{
    private final Repository repository;
    private String lastData;
    private final ReadWriteLock lock;

    public InfoCommand(Repository repository) {
        super("info", "Show info about current repository");
        this.repository = repository;
        this.lastData = "";
        this.lock = new ReentrantReadWriteLock();
    }

    public String getLastData(){
        return lastData;
    }

    @Override
    public boolean execute(Response response) {  // response equals null by default
        lock.readLock().lock();
        try {
            lastData = repository.toString();
            return true;

        } catch (Throwable e) {
            System.out.println("InfoCommand: " + e.getMessage());
            return false;

        } finally {
            lock.readLock().unlock();
        }
    }
}
