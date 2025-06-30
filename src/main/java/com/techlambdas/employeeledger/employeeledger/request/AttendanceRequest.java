package com.techlambdas.employeeledger.employeeledger.request;

import com.techlambdas.employeeledger.employeeledger.constant.AttendanceStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceRequest {
    private LocalDate date;
    private String employeeId;
    private String status;
}
