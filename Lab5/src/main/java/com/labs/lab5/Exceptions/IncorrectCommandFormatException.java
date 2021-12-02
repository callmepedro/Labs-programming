package com.labs.lab5.Exceptions;

public class IncorrectCommandFormatException extends RuntimeException{
    public IncorrectCommandFormatException() {};
    public IncorrectCommandFormatException(String gripe){
        super(gripe);
    }
}
