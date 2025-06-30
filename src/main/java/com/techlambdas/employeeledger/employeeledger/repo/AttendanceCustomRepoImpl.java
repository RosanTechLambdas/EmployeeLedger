package com.techlambdas.employeeledger.employeeledger.repo;

import com.techlambdas.employeeledger.employeeledger.constant.AttendanceStatus;
import com.techlambdas.employeeledger.employeeledger.model.Attendance;
import com.techlambdas.employeeledger.employeeledger.model.Employee;
import com.techlambdas.employeeledger.employeeledger.response.AttendanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceCustomRepoImpl implements AttendanceCustomRepo {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EmployeeRepo  employeeRepo;

    @Override
    public Attendance saveAttendance(Attendance newAttendance) {
        Query query = new Query();
        if(newAttendance.getStatus()== AttendanceStatus.PRESENT){
            query.addCriteria(Criteria.where("employeeId").is(newAttendance.getEmployeeId()));
            Employee existEmployee= mongoTemplate.findOne(query, Employee.class);
            assert existEmployee != null;
            existEmployee.setWorkingDay(existEmployee.getWorkingDay()+1);
            mongoTemplate.save(existEmployee);
        }
        if(newAttendance.getStatus()== AttendanceStatus.HALFDAYPRESENT){
            query.addCriteria(Criteria.where("employeeId").is(newAttendance.getEmployeeId()));
            Employee existEmployee= mongoTemplate.findOne(query, Employee.class);
            assert existEmployee != null;
            existEmployee.setWorkingDay(existEmployee.getWorkingDay()+0.5);
            mongoTemplate.save(existEmployee);
        }
       return mongoTemplate.save(newAttendance);
    }
}
