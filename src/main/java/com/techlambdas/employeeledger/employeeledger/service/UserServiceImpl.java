package com.techlambdas.employeeledger.employeeledger.service;

import com.techlambdas.employeeledger.employeeledger.mapper.UserMapper;
import com.techlambdas.employeeledger.employeeledger.model.User;
import com.techlambdas.employeeledger.employeeledger.repo.UserRepo;
import com.techlambdas.employeeledger.employeeledger.request.UserRequest;
import com.techlambdas.employeeledger.employeeledger.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveUser(UserRequest userRequest) {
        User newUser = userMapper.toEntity(userRequest);
        newUser.setUserId(UUID.randomUUID().toString());
        User existUser = userRepo.findByUserName(userRequest.getUserName());
        if(existUser!=null){
            throw new RuntimeException("User Already Exists");
        }
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        userRepo.save(newUser);
    }
}
