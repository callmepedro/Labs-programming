package com.labs.lab5.Exceptions;

public class ExecuteScriptRecursionException extends RuntimeException {
    public ExecuteScriptRecursionException() {};
    public ExecuteScriptRecursionException(String gripe) {
        super(gripe);
    }
}
