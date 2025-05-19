package com.techlambdas.employeeledger.employeeledger.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AppResponse {
    private static Map<String,Object> ResponseData;

    public static ResponseEntity<?> createResponse(HttpStatus status,
                                                   Optional<Map<String,Object>>  responses,
                                                   Optional<?> errorOptional) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", status.value());
        response.put("status", status);
        response.put("error", errorOptional.orElse(null));
        response.put("result", responses.orElse(null));

        return ResponseEntity.status(status).body(ResponseData(response));
    }

    private static Object ResponseData(Map<String, Object> response) {
        return response;
    }

    public static ResponseEntity<?> successResponse(HttpStatus httpStatus,Map<String,Object> response){
        return createResponse(httpStatus, Optional.of(response), Optional.empty());
    }
    public static ResponseEntity<?> errorResponse(HttpStatus httpStatus,Object message){
        return createResponse(httpStatus, Optional.empty(),Optional.of(message));
    }
}
