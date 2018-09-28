package com.aaxis.ratelimiter.exception;

public class RequestLimiterException extends Exception {

    public RequestLimiterException() {
        super("The number of request has exceeded the limit!");
    }

    public RequestLimiterException(String message){
        super(message);
    }
}
