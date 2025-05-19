package com.techlambdas.employeeledger.employeeledger.config;

import com.techlambdas.employeeledger.employeeledger.exception.UserNotFoundException;
import com.techlambdas.employeeledger.employeeledger.model.User;
import com.techlambdas.employeeledger.employeeledger.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ServiceConfig implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userRepo.findByUserName(s);
        if(user==null){
            throw new UserNotFoundException("User Not Found");
        }
        return new UserPrincipal(user);
    }
}
