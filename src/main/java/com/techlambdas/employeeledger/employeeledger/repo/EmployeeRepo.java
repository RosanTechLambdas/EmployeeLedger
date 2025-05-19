package com.techlambdas.employeeledger.employeeledger.repo;

import com.techlambdas.employeeledger.employeeledger.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepo extends MongoRepository<Employee, String>{
  Employee findByMobileNo(String mobileNo);

  Employee findByEmployeeId(String employeeId);

  Employee findByEmployeeName(String s);
}

