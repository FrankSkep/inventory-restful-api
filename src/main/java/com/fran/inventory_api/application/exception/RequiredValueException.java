package com.fran.inventory_api.application.exception;

public class RequiredValueException extends RuntimeException {
    public RequiredValueException(String message) {
        super(message);
    }
}
