package com.techlambdas.employeeledger.employeeledger.service;

import com.techlambdas.employeeledger.employeeledger.constant.EmployeeStatus;
import com.techlambdas.employeeledger.employeeledger.exception.AlreadyExistsException;
import com.techlambdas.employeeledger.employeeledger.exception.UserNotFoundException;
import com.techlambdas.employeeledger.employeeledger.mapper.EmployeeMapper;
import com.techlambdas.employeeledger.employeeledger.repo.EmployeeCustomRepo;
import com.techlambdas.employeeledger.employeeledger.model.Employee;
import com.techlambdas.employeeledger.employeeledger.repo.EmployeeRepo;
import com.techlambdas.employeeledger.employeeledger.repo.TransactionRepo;
import com.techlambdas.employeeledger.employeeledger.request.EmployeeRequest;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeFinancialReportResponse;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private EmployeeCustomRepo employeeCustomRepo;

    @Autowired
    private TransactionRepo transactionRepo;



    @Autowired
    private EmployeeMapper employeeMapper;

    public List<EmployeeResponse> getEmployee(String status, String employeeName, String mobileNo ,String keyword) {
        List<Employee> getEmployeeResponse= employeeCustomRepo.findEmployee(status, employeeName, mobileNo,keyword);
     return employeeMapper.toListResponse(getEmployeeResponse) ;

    }


    @Override
    public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest){
//        String extension= FilenameUtils.getExtension(image.getOriginalFilename());
//        String fileName=FilenameUtils.removeExtension(image.getOriginalFilename());
//
//        String fullUrl=fileName+"_"+LocalDate.now()+"."+extension ;
//        Path path = Paths.get(System.getProperty("user.dir"), uploadDir, "employeeImage");
//        if(!(Files.exists(path))){
//            Files.createDirectories(path);
//        }
//
//        Path filePath = path.resolve(fullUrl);
//        image.transferTo(filePath.toFile());

        Employee existEmployee=employeeRepo.findByMobileNo(employeeRequest.getMobileNo());
        if(existEmployee!=null)
      {
          throw new AlreadyExistsException ("Mobile number already exists");
      }
        Employee employee=employeeMapper.toEntity(employeeRequest);
        employee.setEmployeeId(UUID.randomUUID().toString());
//        employee.setEmployeeProfile(fullUrl);

        return employeeMapper.toResponse( employeeRepo.save(employee));
    }


    @Override
    public void deleteEmployee(String employeeId) {
        Employee existEmployee = employeeRepo.findByEmployeeId(employeeId);
        if(existEmployee==null){
            throw new UserNotFoundException("InValid Id");
        }
        employeeRepo.deleteById(employeeId);
    }

    @Override
    public EmployeeResponse updateEmployee(EmployeeRequest employeeRequest, String employeeId) {
        Employee existingEmployee = employeeRepo.findByEmployeeId(employeeId);

        if (existingEmployee != null) {
            employeeMapper.updateEmployeeFromRequest(employeeRequest, existingEmployee);
            employeeRepo.save(existingEmployee);
            return employeeMapper.toResponse(existingEmployee);
        } else {
            throw new UserNotFoundException("Invalid Id");
        }
    }



    @Override
    public EmployeeResponse getEmployeeByMobileNo( String mobileNo){
        Employee existEmployee=employeeRepo.findByMobileNo(mobileNo);
        if(existEmployee ==null ){
            throw new UserNotFoundException("InValid Mobile Number");
        }

        return employeeMapper.toResponse(existEmployee);
    }

    @Override
    public Page<EmployeeResponse> getEmployeePage(int page, int size ,String status,String employeeName, String mobileNo){
        Page<Employee> employees= employeeCustomRepo.getEmployee(page,size,status,employeeName,mobileNo);
        List<Employee> employeeContent= employees.getContent();
        return new PageImpl<>(employeeMapper.toListResponse(employeeContent),employees.getPageable(),employees.getTotalElements());
    }

    @Override
    public EmployeeResponse getEmployeeById(String employeeId){
        Employee existEmployee = employeeRepo.findByEmployeeId(employeeId);
        if(existEmployee==null){
            throw new UserNotFoundException("InValid Id");
        }
        return employeeMapper.toResponse(existEmployee);
    }

    @Override
    public byte[] getEmployeeImage(String employeeId) throws IOException {
        Employee employee = employeeRepo.findByEmployeeId(employeeId);
        String fileName = employee.getEmployeeProfile();

        Path path = Paths.get(System.getProperty("user.dir"), uploadDir, "employeeImage", fileName);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: " + path.toString());
        }

        return Files.readAllBytes(path);
    }

    @Override
    public void saveFileEmployee( MultipartFile file,String employeeId) throws IOException {
        Employee existEmployee = employeeRepo.findByEmployeeId(employeeId);
        String extension= FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = FilenameUtils.removeExtension(file.getOriginalFilename());
        String fullUrl = fileName + "_" +LocalDate.now()+ "." +extension ;
        existEmployee.setFileName(fullUrl);
        if(existEmployee==null){
            throw new UserNotFoundException("InValid Id");
        }
       Path path = Paths.get( System.getProperty("user.dir"),uploadDir, "employeeFile");
        if(!(Files.exists(path))){
            Files.createDirectories(path);
        }
        Path filePath = path.resolve(fullUrl);
        file.transferTo(filePath.toFile());
     employeeRepo.save(existEmployee);
    }


    @Override
    public byte[] getEmployeeFile(String employeeId ,String fileName) throws IOException {
        Employee employee = employeeRepo.findByEmployeeId(employeeId);
        if(employee==null){
            throw new UserNotFoundException("InValid Id");
        }
        Path path = Paths.get( uploadDir, "employeeFile", fileName);
        byte[] fileContent = Files.readAllBytes(path);
        return fileContent;
    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {
        Path path = Paths.get( uploadDir, "employeeFile", fileName);
            return Files.readAllBytes(path);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<EmployeeResponse> saveExcelData(MultipartFile fileName)  {
        List<EmployeeResponse> responseList = new ArrayList<>();
        List<Employee> entityList = new ArrayList<>();

        InputStream inputStream;
        Iterator<Row> rowIterator;
        try {
            inputStream = fileName.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            rowIterator=sheet.iterator();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        boolean isFirstRow = true;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Employee employee = new Employee();

            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }

            DataFormatter formatter = new DataFormatter();

            String employeeName = formatter.formatCellValue(row.getCell(0)).trim();
            String mobileNo = formatter.formatCellValue(row.getCell(1)).trim();
            String status = formatter.formatCellValue(row.getCell(2)).trim();
            String address = formatter.formatCellValue(row.getCell(3)).trim();

            if (employeeName.isEmpty()||mobileNo.isEmpty()||status.isEmpty())
            {
                throw new NullPointerException("Employee Name,Mobile No and Status are mandatory");
            }

            if (employeeRepo.findByMobileNo(mobileNo) == null) {
                employee.setEmployeeName(employeeName);
                employee.setMobileNo(mobileNo);
                employee.setAddress(address);
                employee.setStatus(EmployeeStatus.valueOf(status));
                employee.setEmployeeId(UUID.randomUUID().toString());
                employeeRepo.save(employee);
                responseList.add(employeeMapper.toResponse(employee));
            } else {
                Employee existEmployee = employeeRepo.findByMobileNo(mobileNo);
                existEmployee.setEmployeeName(employeeName);
                existEmployee.setMobileNo(mobileNo);
                existEmployee.setAddress(address);
                existEmployee.setStatus(EmployeeStatus.valueOf(status));
                entityList.add(existEmployee);
                employeeRepo.save(existEmployee);
                responseList.add(employeeMapper.toResponse(existEmployee));
            }
        }

//            employeeRepo.saveAll(entityList);

        return responseList;
    }

    @Override
    public List<?> getEmployeeTransactionDetails(){
        return employeeCustomRepo.getEmployeeTransactionDetails();
    }

    @Override
    public List<?> EmployeeFinancialReportResponse(){
        return employeeCustomRepo.getEmployeeFinancialReportResponse();
    }

    @Override
    public byte[] downloadMonthlyReport(LocalDate startingDate, LocalDate endingDate) {
        return PdfGenerator.generateYearlyReportPdf(employeeCustomRepo.downloadMonthlyReport(startingDate,endingDate));
    }
}
