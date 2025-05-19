package com.techlambdas.employeeledger.employeeledger.repo;

import com.techlambdas.employeeledger.employeeledger.model.Employee;
import com.techlambdas.employeeledger.employeeledger.model.Transaction;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionCustomRepo {
    List<Transaction> getTransactionDetailByDate(LocalDate startingDate, LocalDate endindDate);

    Page<Transaction> getTransactionDetailByPage(LocalDate startingDate, LocalDate endingDate, int page, int size);

    List<?> getTotalTransaction();

    List<Document> getLastTransactionDate();

    List<?> getTotalCalculationTransaction();
}
