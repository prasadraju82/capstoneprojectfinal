package com.example.userauthservice.exceptions;

public class PasswordMismatchException extends Exception {
    public PasswordMismatchException(String message) {
        super(message);
    }
}