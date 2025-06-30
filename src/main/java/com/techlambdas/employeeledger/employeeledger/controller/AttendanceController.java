package com.techlambdas.employeeledger.employeeledger.controller;

import com.techlambdas.employeeledger.employeeledger.model.Attendance;
import com.techlambdas.employeeledger.employeeledger.request.AttendanceRequest;
import com.techlambdas.employeeledger.employeeledger.response.AppResponse;
import com.techlambdas.employeeledger.employeeledger.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<?> createAttendance(@RequestBody AttendanceRequest attendance) {
        Map<String,Object> response = new HashMap<>();
        response.put("employeeId", attendanceService.saveAttendance(attendance));
        return AppResponse.successResponse(HttpStatus.ACCEPTED,response);
    }
}
