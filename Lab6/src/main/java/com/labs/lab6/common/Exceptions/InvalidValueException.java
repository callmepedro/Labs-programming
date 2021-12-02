package com.labs.lab6.common.Exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(){};
    public InvalidValueException(String gripe) {
        super(gripe);
    }
}
