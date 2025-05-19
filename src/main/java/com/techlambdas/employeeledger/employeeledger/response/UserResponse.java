package com.techlambdas.employeeledger.employeeledger.response;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserResponse {
    private String userName;
    private String password;
}
