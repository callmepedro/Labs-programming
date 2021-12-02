package com.labs.lab5.AppObjects;

import java.util.Date;
import java.util.Objects;

/**
 * Class of the object that the main repository consist of
 */
public class SpaceMarine implements Comparable{

    private static int counter = 0;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int health; //Значение поля должно быть больше 0
    private boolean loyal;
    private AstartesCategory category; //Поле не может быть null
    private MeleeWeapon meleeWeapon; //Поле не может быть null
    private Chapter chapter; //Поле может быть null

    public SpaceMarine() {};

    public SpaceMarine (String name, Coordinates coordinates, int health,
                        boolean loyal, AstartesCategory category, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.id = ++counter;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.health = health;
        this.loyal = loyal;
        this.category = category;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int x) {
        counter = x;
    }

    /**
     * @return ID of the space marine
     */
    public int getId() {
        return id;
    }

    /**
     * @return Name of the space marine
     */
    public String getName() {
        return name;
    }

    /**
     * @return com.labs.lab5.AppObjects.Coordinates of the space marine
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return Creation date of the space marine
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @return Health of the space marine
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return Loyal of the space marine
     */
    public boolean getLoyal() {
        return loyal;
    }

    /**
     * @return Category of space marine
     */
    public AstartesCategory getCategory() {
        return category;
    }

    /**
     * @return com.labs.lab5.AppObjects.MeleeWeapon of the space marine
     */
    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    /**
     * @return com.labs.lab5.AppObjects.Chapter of the space marine
     */
    public Chapter getChapter() {
        return chapter;
    }

    @Override
    public boolean equals(Object otherObject){
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;

        SpaceMarine other = (SpaceMarine)otherObject;

        return id == other.id && name.equals(other.name) && coordinates.equals(other.coordinates) &&
                creationDate.equals(other.creationDate) && health == other.health && loyal == other.loyal &&
                category.equals(other.category) && meleeWeapon.equals(other.meleeWeapon) && chapter.equals(other.chapter);
    }

    @Override
    public int hashCode() {
        int hash = Objects.hash(id, name, coordinates, creationDate, health, loyal, category, meleeWeapon, chapter);
        return hash;
    }

    @Override
    public String toString(){
        String description = "Description of the space marine №" + id;

        description += "\n..Name: " + name;
        description += "\n..Coordinates: " + coordinates;
        description += "\n..Creation date: " + creationDate;
        description += "\n..Health: " + health;
        description += "\n..Loyal: " + loyal;
        description += "\n..Category: " + category;
        description += "\n..Melee weapon: " + meleeWeapon;
        description += "\n..Chapter: " + chapter;

        return description;
    }

    @Override
    public int compareTo(Object otherObject) {
        SpaceMarine other = (SpaceMarine)otherObject;
        return Integer.compare(this.getHealth(), other.getHealth());
    }
}
