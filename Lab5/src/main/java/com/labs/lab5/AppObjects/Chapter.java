package com.labs.lab5.AppObjects;

import java.util.Objects;

/**
 * Chapter of space marines
 */
public class Chapter {

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long marinesCount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 1000

    public Chapter (String name, Long marinesCount) {
        this.name = name;
        this.marinesCount = marinesCount;
    }

    /**
     * @return Name of the chapter
     */
    public String getName() {
        return name;
    }

    /**
     * @return Count of marines of the chapter
     */
    public Long getMarinesCount() {
        return marinesCount;
    }

    @Override
    public boolean equals(Object otherObject){
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;

        Chapter other = (Chapter)otherObject;

        return name.equals(other.name) && marinesCount.equals(other.marinesCount);
    }

    @Override
    public int hashCode(){
        int hash = Objects.hash(name, marinesCount);
        return hash;
    }

    @Override
    public String toString(){
        String description = String.format("%s [have %s marines]", name, marinesCount);
        return description;
    }
}

