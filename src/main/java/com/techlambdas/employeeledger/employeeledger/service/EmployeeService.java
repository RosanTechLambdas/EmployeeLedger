package com.techlambdas.employeeledger.employeeledger.service;

import com.techlambdas.employeeledger.employeeledger.request.EmployeeRequest;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeFinancialReportResponse;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public interface EmployeeService {
    List<EmployeeResponse> getEmployee(String status,String employeeName, String mobileNo,String keyword);

    EmployeeResponse saveEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse updateEmployee(EmployeeRequest employeeRequest, String employeeId) ;

    Page<EmployeeResponse> getEmployeePage(int page, int size,String status,String employeeName, String mobileNo);

    EmployeeResponse getEmployeeById(String employeeId);

    EmployeeResponse getEmployeeByMobileNo(String mobileNo);

    void deleteEmployee(String employeeId);

    byte[] getEmployeeImage(String employeeId) throws IOException;

    void saveFileEmployee( MultipartFile file,String employeeId) throws IOException;


    byte[] getEmployeeFile(String employeeId, String fileName) throws IOException;

    byte[] downloadFile(String fileName) throws IOException;

    List<EmployeeResponse> saveExcelData(MultipartFile fileName) ;


    List<?> getEmployeeTransactionDetails();

    List<?> EmployeeFinancialReportResponse();

    byte[] downloadMonthlyReport(LocalDate StartDate, LocalDate EndDate);
}
