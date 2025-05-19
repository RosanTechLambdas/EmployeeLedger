package com.techlambdas.employeeledger.employeeledger.controller;

import com.techlambdas.employeeledger.employeeledger.request.EmployeeRequest;
import com.techlambdas.employeeledger.employeeledger.response.AppResponse;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeResponse;
import com.techlambdas.employeeledger.employeeledger.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getEmployee(@RequestParam(required = false) String status,
                                         @RequestParam(required = false) String employeeName,
                                         @RequestParam(required = false) String mobileNo,
                                         @RequestParam(required = false) String keyword)
    {

        Map<String, Object> response = new HashMap<>();
        response.put("employees", employeeService.getEmployee(status, employeeName, mobileNo,keyword));
        return AppResponse.successResponse(HttpStatus.OK, response);
    }

    @GetMapping("getEmployeeByMobileNo")
    public ResponseEntity<?> getEmployeeByMobileNo(@RequestParam String mobileNo) {
        Map<String, Object> response = new HashMap<>();
        response.put("employee", employeeService.getEmployeeByMobileNo(mobileNo));
        return AppResponse.successResponse(HttpStatus.OK, response);
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestPart("employee") EmployeeRequest employeeRequest,
                                          @RequestPart("image") MultipartFile image) throws IOException {
        Map<String, Object> response = new HashMap<>();
        response.put("employee", employeeService.saveEmployee(employeeRequest, image));
        return AppResponse.successResponse(HttpStatus.CREATED, response);

    }

    @GetMapping("/getImage/{employeeId}")
    public ResponseEntity <?> getEmployeeImage(@PathVariable String employeeId) throws IOException {
        byte[] bytes=employeeService.getEmployeeImage(employeeId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\" " + "techLambdas" + "\"")
                .body(bytes);
    }


    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteEmployee(employeeId);
        Map<String, Object> response = new HashMap<>();
        response.put("employee", "employee deleted");
        return AppResponse.successResponse(HttpStatus.OK, response);
    }

    @PutMapping("{employeeId}")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeRequest employeeRequest, @PathVariable String employeeId) {
        Map<String, Object> response = new HashMap<>();
        response.put("Employee", employeeService.updateEmployee(employeeRequest, employeeId));
        return AppResponse.successResponse(HttpStatus.CREATED, response);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getEmployeePage(@RequestParam(required = false) int page,
                                             @RequestParam(required = false) int size,
                                             @RequestParam(required = false) String status,
                                             @RequestParam(required = false) String employeeName,
                                             @RequestParam(required = false) String mobileNo) {
        Map<String, Object> response = new HashMap<>();
        response.put("employees", employeeService.getEmployeePage(page, size, status, employeeName, mobileNo));
        return AppResponse.successResponse(HttpStatus.OK, response);
    }

    @GetMapping("{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String employeeId) {
        Map<String, Object> response = new HashMap<>();
        response.put("employee", employeeService.getEmployeeById(employeeId));
        return AppResponse.successResponse(HttpStatus.OK, response);
    }

    @PostMapping("/employeeFile/{employeeId}")
    public ResponseEntity<?> savePdfFile(@PathVariable String employeeId,
                                         @RequestPart("file") MultipartFile file) throws IOException {
        employeeService.saveFileEmployee(file,employeeId);
        Map<String, Object> response = new HashMap<>();
        response.put("message","succesfully upload");
        return AppResponse.successResponse(HttpStatus.CREATED,response);
    }

    @GetMapping("/getEmployeeFile/{employeeId}/{fileName}")
    public ResponseEntity<?> getEmployeeFile(@PathVariable String employeeId,@PathVariable String fileName) throws IOException {
        byte[] bytes=employeeService.getEmployeeFile(employeeId,fileName);
        String ContentType= URLConnection.guessContentTypeFromName(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE,ContentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\" " + "techLambdas" + "\"")
                .body(bytes);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) throws IOException {
          byte[]data=employeeService.downloadFile(fileName);
          return ResponseEntity.ok()
                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" " + fileName + "\"")
                  .body(data);
    }

    @PostMapping("/uploadExcelFile")
    public ResponseEntity<?> uploadExcelFile(@RequestPart("file") MultipartFile file) {
        List<EmployeeResponse>list=employeeService.saveExcelData(file);
        Map<String,Object> response=new HashMap<>();
        response.put("message",list);
        return AppResponse.successResponse(HttpStatus.OK,response);
    }
    @GetMapping("/getEmployeeTransactionDetails")
    public ResponseEntity<?> getEmployeeTransactionDetails(){
        Map<String,Object> response=new HashMap<>();
        response.put("employee",employeeService.getEmployeeTransactionDetails());
        return AppResponse.successResponse(HttpStatus.OK,response);
    }

    @GetMapping("/EmployeeFinancialReportResponse")
    public ResponseEntity<?> EmployeeFinancialReportResponse(){
        Map<String,Object> response=new HashMap<>();
       response.put("employee",employeeService.EmployeeFinancialReportResponse());
       return AppResponse.successResponse(HttpStatus.OK,response);
    }
}
