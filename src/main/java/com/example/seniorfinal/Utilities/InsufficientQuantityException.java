package com.example.seniorfinal.Utilities;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException(String msg) {
        super(msg);
    }
}
