package com.techlambdas.employeeledger.employeeledger.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionRequest {
    private String employeeId;
    private double rate;
    private double messBill;
    private double accountPaidAmount;
    private double paidAmount;
    private double advanceAmount;
    private double depositAmount;
    private double balanceAmount;
}
