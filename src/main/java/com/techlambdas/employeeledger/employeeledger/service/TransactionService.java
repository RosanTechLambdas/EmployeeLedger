package com.techlambdas.employeeledger.employeeledger.service;

import com.techlambdas.employeeledger.employeeledger.model.Transaction;
import com.techlambdas.employeeledger.employeeledger.request.TransactionRequest;
import com.techlambdas.employeeledger.employeeledger.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    TransactionResponse getTransactionDetailsByEmployeeId(String transactionId);

    TransactionResponse updateTransactionDetailsByEmployeeId(TransactionRequest transactionRequest, String transactionId);

    void deleteTransactionDetailsByEmployeeId(String transactionId);

    TransactionResponse saveTransactionDetails(TransactionRequest transactionRequest);

    List<TransactionResponse> getTransactionDetails(LocalDate startingDate, LocalDate endingDate);

    Page<TransactionResponse> getTransactionDetailsByPage(LocalDate startingDate, LocalDate endingDate, int page, int size);

    List<?> getTotalTransaction();

    List<?> getLastTransactionDate();

    List<?> getTotalCalculationTransaction();
}
