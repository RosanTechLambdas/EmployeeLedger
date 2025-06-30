package com.techlambdas.employeeledger.employeeledger.model;

import com.techlambdas.employeeledger.employeeledger.constant.EmployeeStatus;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "employee")
public class Employee {
    @Id
    private String id;
    private String employeeId;
    private String employeeName;
    @Indexed(unique = true)
    private String mobileNo;
    private EmployeeStatus status;
    private String Address;
    private double workingDay;
    @CreatedDate
    private LocalDateTime createdDateTime;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime updatedDateTime;
    @LastModifiedBy
    private String updatedBy;
    private String employeeProfile;
    private String fileName;

}
