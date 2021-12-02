package com.labs.lab7.Server.Commands;

import com.labs.lab7.common.Response;

/**
 * interface for pattern 'Command'
 */
public interface Command {
    boolean execute(Response o);
    String getName();
    String getDescription();
}
