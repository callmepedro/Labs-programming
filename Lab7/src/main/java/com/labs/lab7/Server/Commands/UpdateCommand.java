package com.labs.lab7.Server.Commands;

import com.labs.lab7.common.AppObjects.SpaceMarine;
import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Update element of repository by ID
 */
public class UpdateCommand extends AbstractCommand {
    private final Repository repository;
    private final ReadWriteLock lock;

    public UpdateCommand(Repository repository) {
        super("update id {element}", "Update value of Repository's element by ID");
        this.repository = repository;
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public boolean execute(Response response) {
        SpaceMarine spaceMarine = (SpaceMarine) response.getCommandArg();

        int id = Integer.parseInt(response.getCommandStruct().getArgument());
        spaceMarine.setId(id);

        lock.writeLock().lock();
        try {
            for (int i = 0; i < repository.size(); ++i) {
                if (repository.getList().get(i).getId() == id) {
                    String login = repository.getList().get(i).getInitData().getLogin();
                    if (!login.equals(response.getInitData().getLogin()))
                        return false;

                    repository.getList().set(i, spaceMarine);
                    return true;
                }
            }
            return false;

        } catch (Throwable e){
            System.out.println("UpdateCommand: " + e.getMessage());
            return false;

        } finally {
            lock.writeLock().unlock();
        }
    }
}