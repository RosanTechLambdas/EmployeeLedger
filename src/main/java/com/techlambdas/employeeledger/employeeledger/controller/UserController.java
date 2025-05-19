package com.techlambdas.employeeledger.employeeledger.controller;


import com.techlambdas.employeeledger.employeeledger.jwtToken.JwtFilter;
import com.techlambdas.employeeledger.employeeledger.jwtToken.Tokenservice;
import com.techlambdas.employeeledger.employeeledger.request.UserRequest;
import com.techlambdas.employeeledger.employeeledger.response.AppResponse;
import com.techlambdas.employeeledger.employeeledger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Tokenservice tokenservice;

   @PostMapping("/register")
   public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest){
       HashMap<String,Object> response = new HashMap<>();
       userService.saveUser(userRequest);
       response.put("message","User Registered Successfully");

       return AppResponse.successResponse(HttpStatus.CREATED,response);
   }

   @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest){
       System.out.println("user request "+userRequest.getUserName()+" password "+userRequest.getPassword());
        authenticationManager.authenticate
               (new UsernamePasswordAuthenticationToken(userRequest.getUserName(),userRequest.getPassword()));

           String token=tokenservice.generateToken(userRequest.getUserName());
           HashMap<String,Object> response = new HashMap<>();
           response.put("token",token);
           response.put("message","login successful");
           return AppResponse.successResponse(HttpStatus.OK,response);
    }

}
