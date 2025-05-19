package com.techlambdas.employeeledger.employeeledger.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String message, int status ,LocalDateTime localDateTime) {
        this.message = message;
        this.statusCode = status;
        this.timestamp = localDateTime;
    }
}
