package com.techlambdas.employeeledger.employeeledger.mapper;

import com.techlambdas.employeeledger.employeeledger.model.Employee;
import com.techlambdas.employeeledger.employeeledger.model.Transaction;
import com.techlambdas.employeeledger.employeeledger.repo.EmployeeRepo;
import com.techlambdas.employeeledger.employeeledger.request.TransactionRequest;
import com.techlambdas.employeeledger.employeeledger.response.TransactionResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Mapping(target = "employeeName", source = "employeeId", qualifiedByName = "EmployeeName" )
    @Mapping(target = "workingDays" ,source = "employeeId", qualifiedByName ="WorkingDays")
   public abstract TransactionResponse toRespone(Transaction transaction);
    @Mapping(target = "workingDays" ,source = "employeeId", qualifiedByName ="WorkingDays")
    public abstract Transaction toEntity(TransactionRequest transactionRequest);
    public abstract List<TransactionResponse> toListResponse(List<Transaction> transactions);

    @Named("EmployeeName")
    public String EmployeeToString(String employeeId){
        return employeeRepo.findByEmployeeId(employeeId).getEmployeeName();
    }

    @Named("WorkingDays")
    public double EmployeeToInt(String employeeId){
        return employeeRepo.findByEmployeeId(employeeId).getWorkingDay();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void upadateTransactionData(TransactionRequest transactionRequest,@MappingTarget Transaction existTransaction);
}
