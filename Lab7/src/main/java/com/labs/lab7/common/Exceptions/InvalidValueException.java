package com.labs.lab7.common.Exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(){};
    public InvalidValueException(String gripe) {
        super(gripe);
    }
}
