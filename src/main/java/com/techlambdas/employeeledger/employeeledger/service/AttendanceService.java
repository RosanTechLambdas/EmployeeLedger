package com.techlambdas.employeeledger.employeeledger.service;

import com.techlambdas.employeeledger.employeeledger.model.Attendance;
import com.techlambdas.employeeledger.employeeledger.request.AttendanceRequest;
import com.techlambdas.employeeledger.employeeledger.response.AttendanceResponse;

public interface AttendanceService {
    AttendanceResponse saveAttendance(AttendanceRequest attendance);
}
