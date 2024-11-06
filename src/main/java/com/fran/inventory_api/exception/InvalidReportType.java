package com.fran.inventory_api.exception;

public class InvalidReportType extends RuntimeException {
    public InvalidReportType(String message) {
        super(message);
    }
}
