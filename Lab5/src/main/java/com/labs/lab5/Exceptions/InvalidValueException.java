package com.labs.lab5.Exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(){};
    public InvalidValueException(String gripe) {
        super(gripe);
    }
}
