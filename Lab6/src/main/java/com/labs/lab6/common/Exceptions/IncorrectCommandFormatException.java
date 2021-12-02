package com.labs.lab6.common.Exceptions;

public class IncorrectCommandFormatException extends RuntimeException{
    public IncorrectCommandFormatException() {};
    public IncorrectCommandFormatException(String gripe){
        super(gripe);
    }
}
