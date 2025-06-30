package com.techlambdas.employeeledger.employeeledger.model;

import com.techlambdas.employeeledger.employeeledger.constant.AttendanceStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "attendance")
public class Attendance {
    @Id
    private String id;
    private String attendanceId;
    private LocalDate date;
    private String employeeId;
    private AttendanceStatus status;
}
