package com.techlambdas.employeeledger.employeeledger.request;

import com.techlambdas.employeeledger.employeeledger.constant.EmployeeStatus;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class EmployeeRequest {
    private String employeeName;
    private String mobileNo;
    private String status;
}
