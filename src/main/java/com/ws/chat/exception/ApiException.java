package com.ws.chat.exception;

public class ApiException extends RuntimeException {

    public ApiException(String msg) {
        super(msg);
    }
}
