package com.techlambdas.employeeledger.employeeledger.mapper;

import com.techlambdas.employeeledger.employeeledger.model.Transaction;
import com.techlambdas.employeeledger.employeeledger.repo.TransactionRepo;
import com.techlambdas.employeeledger.employeeledger.request.EmployeeRequest;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeResponse;
import com.techlambdas.employeeledger.employeeledger.model.Employee;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {
     @Autowired
     private TransactionRepo transactionRepo;

     @Mapping(target = "depositAmount", source = "employeeId", qualifiedByName = "getDeposit")
     @Mapping(target = "advanceAmount", source = "employeeId", qualifiedByName = "getAdvance")
     @Mapping(target = "transactionDate", source = "employeeId", qualifiedByName = "getTransactionDate")
     public abstract EmployeeResponse toResponse(Employee employee);

     public abstract Employee toEntity(EmployeeRequest employeeRequest);

     public abstract List<EmployeeResponse> toListResponse(List<Employee> employees);

     @Named("getDeposit")
     public double getDeposit(String employeeId) {
          List<Transaction> txn = transactionRepo.findByEmployeeId(employeeId);
          if (txn == null || txn.isEmpty()) {
               return 0.0;
          }
          double val= txn.get(txn.size() - 1).getBalanceAmount();
          if(val<0){
               return 0.0;
          }
          return val;
     }

     @Named("getAdvance")
     public double getAdvance(String employeeId) {
          List<Transaction> txn = transactionRepo.findByEmployeeId(employeeId);
          if (txn == null || txn.isEmpty()) {
               return 0.0;
          }
          double val=txn.get(txn.size() - 1).getBalanceAmount();
          if(val>0){
               return 0.0;
          }
          return Math.abs(val);
     }

     @Named("getTransactionDate")
     public LocalDate getTransactionDate(String employeeId) {
          List<Transaction> txn = transactionRepo.findByEmployeeId(employeeId);
          if (txn == null || txn.isEmpty()) {
               return null;
          }
          return txn.get(txn.size() - 1).getTransactionDate();
     }


     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     public abstract void updateEmployeeFromRequest(EmployeeRequest request, @MappingTarget Employee employee);

}
