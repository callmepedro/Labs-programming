package com.labs.lab7.common.Exceptions;

public class TimeoutException extends RuntimeException{
    public TimeoutException() {};
    public TimeoutException(String msg) {
        super(msg);
    }
}
