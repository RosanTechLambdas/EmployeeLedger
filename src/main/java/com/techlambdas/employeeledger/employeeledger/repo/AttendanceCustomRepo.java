package com.techlambdas.employeeledger.employeeledger.repo;

import com.techlambdas.employeeledger.employeeledger.model.Attendance;
import com.techlambdas.employeeledger.employeeledger.response.AttendanceResponse;

public interface AttendanceCustomRepo {
    Attendance saveAttendance(Attendance newAttendance);
}
