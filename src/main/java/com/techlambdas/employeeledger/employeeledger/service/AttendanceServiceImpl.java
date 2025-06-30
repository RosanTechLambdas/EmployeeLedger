package com.techlambdas.employeeledger.employeeledger.service;

import com.techlambdas.employeeledger.employeeledger.mapper.AttendanceMapper;
import com.techlambdas.employeeledger.employeeledger.model.Attendance;
import com.techlambdas.employeeledger.employeeledger.repo.AttendanceCustomRepo;
import com.techlambdas.employeeledger.employeeledger.request.AttendanceRequest;
import com.techlambdas.employeeledger.employeeledger.response.AttendanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceCustomRepo attendanceCustomRepo;

    @Autowired
    private AttendanceMapper attendanceMapper;


    @Override
    public AttendanceResponse saveAttendance(AttendanceRequest attendance) {
        Attendance newAttendance = attendanceMapper.toAttendance(attendance);
        newAttendance.setAttendanceId(UUID.randomUUID().toString());
        AttendanceResponse attendanceResponse= attendanceMapper.toAttendanceResponse(attendanceCustomRepo.saveAttendance(newAttendance));
        return attendanceResponse;
    }
}
