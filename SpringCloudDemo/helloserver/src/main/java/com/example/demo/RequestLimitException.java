package com.example.demo;

public class RequestLimitException extends Exception {

    public RequestLimitException() {
        super("The number of request has exceeded the limit");
    }

    public RequestLimitException(String message){
        super(message);
    }
}
