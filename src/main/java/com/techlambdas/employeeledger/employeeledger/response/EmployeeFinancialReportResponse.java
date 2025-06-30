package com.techlambdas.employeeledger.employeeledger.response;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeFinancialReportResponse {
    private String employeeId;
    private String employeeName;
    private String employeeMobile;
    private Integer totalWorkingDays;
    private Integer totalAmountPaid;
    private Integer totalBalanceAmount;
    private Integer totalMessBill;
    private Double averageRate;
    private Integer totalTransactions;
    private List<MonthlyReport> monthlyReport;

}
