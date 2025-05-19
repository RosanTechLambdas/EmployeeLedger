package com.techlambdas.employeeledger.employeeledger.service;

import com.techlambdas.employeeledger.employeeledger.request.UserRequest;
import com.techlambdas.employeeledger.employeeledger.response.UserResponse;

public interface UserService {
    void saveUser(UserRequest userRequest);
}
