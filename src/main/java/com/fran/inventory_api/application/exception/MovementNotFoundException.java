package com.fran.inventory_api.application.exception;

public class MovementNotFoundException extends RuntimeException {
    public MovementNotFoundException(String message) {
        super(message);
    }
}
