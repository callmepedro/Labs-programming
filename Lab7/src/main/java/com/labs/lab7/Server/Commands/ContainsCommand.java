package com.labs.lab7.Server.Commands;

import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ContainsCommand extends AbstractCommand{
    private final Repository repository;
    private final ReadWriteLock lock;
    private String lastData = null;

    public ContainsCommand(Repository repository) {
        super("contains", "check if repository contains element with such id");
        this.repository = repository;
        this.lock = new ReentrantReadWriteLock();
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Response response) {
        lock.readLock().lock();
        try {
            int id = Integer.parseInt(response.getCommandStruct().getArgument());
            for (SpaceMarine elem : repository.getList()) {
                if (elem.getId() == id) {
                    String login = elem.getInitData().getLogin();
                    if (!login.equals(response.getInitData().getLogin())){
                        lastData = "Current user have no access to this element";
                        return false;
                    }
                    return true;
                }
            }
            lastData = "There is no such element with this ID";
            return false;

        } catch (Throwable e){
            System.out.println("ContainsCommand: " + e.getMessage());
            return false;

        } finally {
            lock.readLock().unlock();
        }
    }
}
