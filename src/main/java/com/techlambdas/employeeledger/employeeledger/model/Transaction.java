package com.techlambdas.employeeledger.employeeledger.model;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "transaction")
public class Transaction {
    @Id
    private String id;
    private String transactionId;
    private String employeeId;
    private LocalDate transactionDate;
    private double workingDays;
    private double rate;
    private double messBill;
    private double accountPaidAmount;
    private double paidAmount;
    private double advanceAmount;
    private double depositAmount;
    private double balanceAmount;
    @CreatedDate
    private LocalDateTime createdDateTime;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime updatedDateTime;
    @LastModifiedBy
    private String updatedBy;
}
