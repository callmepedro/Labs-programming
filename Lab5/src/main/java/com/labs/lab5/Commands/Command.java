package com.labs.lab5.Commands;

/**
 * interface for pattern 'Command'
 */
public interface Command {
    boolean execute(Object o);
    String getName();
    String getDescription();
}
