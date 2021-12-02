package com.labs.lab5.AppObjects;


import java.util.Objects;

/**
 * Coordinates of the space marine
 */
public class Coordinates {
    private double x;
    private Double y; //Максимальное значение поля: 219, Поле не может быть null

    public Coordinates(double x, Double y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return X coordinate of marine
     */
    public double getX() {
        return x;
    }

    /**
     * @return Y coordinate of marine
     */
    public Double getY() {
        return y;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;

        Coordinates other = (Coordinates)otherObject;

        return x == other.x && y.equals(other.y);
    }

    @Override
    public int hashCode(){
        int hash = Objects.hash(x, y);
        return hash;
    }

    @Override
    public String toString(){
        String description = String.format("(%s, %s)", x, y);
        return description;
    }

}