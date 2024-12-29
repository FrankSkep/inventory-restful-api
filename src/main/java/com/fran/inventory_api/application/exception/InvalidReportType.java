package com.fran.inventory_api.application.exception;

public class InvalidReportType extends RuntimeException {
    public InvalidReportType(String message) {
        super(message);
    }
}
