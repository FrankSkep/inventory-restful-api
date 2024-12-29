package com.fran.inventory_api.application.exception;

public class FileOperationErrorException extends RuntimeException {
    public FileOperationErrorException(String message) {
        super(message);
    }
}
