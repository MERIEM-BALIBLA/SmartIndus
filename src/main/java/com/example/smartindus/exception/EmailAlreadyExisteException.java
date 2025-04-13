package com.example.smartindus.exception;

public   class EmailAlreadyExisteException extends RuntimeException {
    public EmailAlreadyExisteException(String message) {
        super(message);
    }

}