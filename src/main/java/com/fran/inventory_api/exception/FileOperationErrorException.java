package com.fran.inventory_api.exception;

public class FileOperationErrorException extends RuntimeException {
    public FileOperationErrorException(String message) {
        super(message);
    }
}
