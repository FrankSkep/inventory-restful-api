package com.fran.inventory_api.system.exception;

public class InvalidReportType extends RuntimeException {
    public InvalidReportType(String message) {
        super(message);
    }
}
