package com.fran.inventory_api.system.exception;

public class RequiredValueException extends RuntimeException {
    public RequiredValueException(String message) {
        super(message);
    }
}
