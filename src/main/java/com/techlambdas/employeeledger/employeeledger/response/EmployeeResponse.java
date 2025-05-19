package com.techlambdas.employeeledger.employeeledger.response;

import com.techlambdas.employeeledger.employeeledger.constant.EmployeeStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeResponse {
    private String employeeId;
    private String employeeName;
    private String mobileNo;
    private String status;
    private double depositAmount;
    private double advanceAmount;
    private LocalDate transactionDate;

}
