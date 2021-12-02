package com.labs.lab7.common.Exceptions;

public class IncorrectCommandFormatException extends RuntimeException{
    public IncorrectCommandFormatException() {};
    public IncorrectCommandFormatException(String gripe){
        super(gripe);
    }
}
