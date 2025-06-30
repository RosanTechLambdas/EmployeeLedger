package com.techlambdas.employeeledger.employeeledger.repo;

import com.techlambdas.employeeledger.employeeledger.model.Employee;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeFinancialReportResponse;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeCustomRepo {

   List<Employee> findEmployee(String status, String employeeName, String mobileNo,String keyword);
   Page<Employee> getEmployee(int page, int size,String status,String employeeName, String mobileNo);

    List<?> getEmployeeTransactionDetails();

    List<?> getEmployeeFinancialReportResponse();

    List<EmployeeFinancialReportResponse> downloadMonthlyReport(LocalDate startingDate, LocalDate endingDate);
}
