package com.techlambdas.employeeledger.employeeledger.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionResponse {
    private String transactionId;
    private String employeeId;
    private String employeeName;
    private LocalDate transactionDate;
    private double workingDays;
    private double rate;
    private double messBill;
    private double totalAmount;
    private double accountPaidAmount;
    private double paidAmount;
    private double advanceAmount;
    private double depositAmount;
    private double balanceAmount;
}
