package com.techlambdas.employeeledger.employeeledger.repo;

import com.techlambdas.employeeledger.employeeledger.model.User;
import com.techlambdas.employeeledger.employeeledger.response.UserResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {
    UserResponse findByUserId(String userId);

    User findByUserName(String userName);
}
