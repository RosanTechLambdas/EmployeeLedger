package com.techlambdas.employeeledger.employeeledger.response;

import lombok.Data;

@Data
public class MonthlyReport {
    private String startDate;
    private String endDate;
    private Integer PresentDays;
    private Integer absentDays;
}
