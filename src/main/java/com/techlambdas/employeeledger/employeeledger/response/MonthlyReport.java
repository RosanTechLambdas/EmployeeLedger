package com.techlambdas.employeeledger.employeeledger.response;

import lombok.Data;

@Data
public class MonthlyReport {
    private String month;
    private Integer paidAmount;
    private Integer balanceAmount;
    private Integer workingDays;
    private Integer messBill;

}
