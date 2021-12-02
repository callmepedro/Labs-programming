package com.labs.lab7.Server.Utils;

import com.labs.lab7.common.AppObjects.SpaceMarine;

import java.util.*;

/**
 * Class of main repository
 */

public class Repository {

    private List<SpaceMarine> repository;
    private Date initializationDate;

    public Repository(){};

    public Repository(List<SpaceMarine> listType) {
        repository = listType;
        initializationDate = new Date();
    }

    /**
     * @return Current list example of repository
     */
    synchronized public List<SpaceMarine> getList() {
        return repository;
    }

    /**
     * @return size of repository
     */
    synchronized public int size(){
        return repository.size();
    }


    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;

        Repository other = (Repository) otherObject;
        return repository.equals(other.repository) && initializationDate.equals(other.initializationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repository, initializationDate);
    }

    @Override
    public String toString() {
        return "'Repository info'" +
                "\nType: " + repository.getClass().getName() +
                "\nSize: " + this.size() +
                "\nInitialization Date: " + initializationDate;
    }
}