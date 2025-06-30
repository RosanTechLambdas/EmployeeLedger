package com.techlambdas.employeeledger.employeeledger.mapper;


import com.techlambdas.employeeledger.employeeledger.model.Attendance;
import com.techlambdas.employeeledger.employeeledger.request.AttendanceRequest;
import com.techlambdas.employeeledger.employeeledger.response.AttendanceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AttendanceMapper {
    public abstract AttendanceResponse toAttendanceResponse(Attendance attendance);
    public abstract Attendance  toAttendance(AttendanceRequest attendanceRequest);
}
