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
   public abstract TransactionResponse toRespone(Transaction transaction);
    public abstract Transaction toEntity(TransactionRequest transactionRequest);
    public abstract List<TransactionResponse> toListResponse(List<Transaction> transactions);

    @Named("EmployeeName")
    public String EmployeeToString(String employeeId){
        return employeeRepo.findByEmployeeId(employeeId).getEmployeeName();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void upadateTransactionData(TransactionRequest transactionRequest,@MappingTarget Transaction existTransaction);
}
