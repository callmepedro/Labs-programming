package com.labs.lab6.common.Exceptions;

public class TimeoutException extends RuntimeException{
    public TimeoutException() {};
    public TimeoutException(String msg) {
        super(msg);
    }
}
