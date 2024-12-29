package com.fran.inventory_api.system.exception;

public class FileOperationErrorException extends RuntimeException {
    public FileOperationErrorException(String message) {
        super(message);
    }
}
