package com.techlambdas.employeeledger.employeeledger.mapper;

import com.techlambdas.employeeledger.employeeledger.model.User;
import com.techlambdas.employeeledger.employeeledger.request.UserRequest;
import com.techlambdas.employeeledger.employeeledger.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest userRequest);
    UserResponse toResponse(User user);
    UserRequest toReqest(User user);
}
