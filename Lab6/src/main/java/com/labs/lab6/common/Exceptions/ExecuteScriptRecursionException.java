package com.labs.lab6.common.Exceptions;

public class ExecuteScriptRecursionException extends RuntimeException {
    public ExecuteScriptRecursionException() {};
    public ExecuteScriptRecursionException(String gripe) {
        super(gripe);
    }
}
