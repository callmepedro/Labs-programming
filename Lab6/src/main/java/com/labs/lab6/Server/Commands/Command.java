package com.labs.lab6.Server.Commands;

import com.labs.lab6.common.Request;

/**
 * interface for pattern 'Command'
 */
public interface Command {
    boolean execute(Request o);
    String getName();
    String getDescription();
}
