package com.example.smartindus.exception;

public class AuthenticationException extends RuntimeException {
    private final String errorType;

    public AuthenticationException(String message, String errorType) {
        super(message);
        this.errorType = errorType;
    }

    public String getErrorType() {
        return errorType;
    }

    public static final String EMAIL_NOT_FOUND = "EMAIL_NOT_FOUND";
    public static final String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
}