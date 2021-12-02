package com.labs.lab5.Commands;


/**
 * Abstract class which contains name and description of any Command
 */
public abstract class AbstractCommand implements Command{
    String name;
    String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return Name of the Command
     */
    public String getName() {
        return name;
    }

    /**
     * @return Description of the Command
     */
    public String getDescription() {
        return description;
    }
}
