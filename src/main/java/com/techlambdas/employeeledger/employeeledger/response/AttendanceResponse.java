package com.techlambdas.employeeledger.employeeledger.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceResponse {
    private LocalDate date;
    private String employeeId;
    private String status;
}
