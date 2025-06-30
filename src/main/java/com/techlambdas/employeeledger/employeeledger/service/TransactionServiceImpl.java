package com.techlambdas.employeeledger.employeeledger.service;

import com.techlambdas.employeeledger.employeeledger.exception.UserNotFoundException;
import com.techlambdas.employeeledger.employeeledger.mapper.TransactionMapper;
import com.techlambdas.employeeledger.employeeledger.model.Employee;
import com.techlambdas.employeeledger.employeeledger.model.Transaction;
import com.techlambdas.employeeledger.employeeledger.repo.EmployeeRepo;
import com.techlambdas.employeeledger.employeeledger.repo.TransactionCustomRepo;
import com.techlambdas.employeeledger.employeeledger.repo.TransactionRepo;
import com.techlambdas.employeeledger.employeeledger.request.TransactionRequest;
import com.techlambdas.employeeledger.employeeledger.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private TransactionCustomRepo transactionCustomRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public TransactionResponse getTransactionDetailsByEmployeeId(String transactionId) {
        Transaction existTransaction = transactionRepo.findByTransactionId(transactionId);
        if (existTransaction == null) {
            throw new UserNotFoundException("InValid EmployeeId");
        }
        return transactionMapper.toRespone(existTransaction);
    }

    @Override
    public TransactionResponse updateTransactionDetailsByEmployeeId(TransactionRequest transactionRequest, String transactionId) {
        Transaction existTransaction = transactionRepo.findByTransactionId(transactionId);
        if (existTransaction == null) {
            throw new UserNotFoundException("InValid EmployeeId");
        }
       transactionMapper.upadateTransactionData(transactionRequest,existTransaction);
        transactionRepo.save(existTransaction);
        return transactionMapper.toRespone(existTransaction);
    }

    @Override
    public void deleteTransactionDetailsByEmployeeId(String transactionId) {
        Transaction existTransaction = transactionRepo.findByTransactionId(transactionId);
        if (existTransaction == null) {
            throw new UserNotFoundException("InValid EmployeeId");
        }
        transactionRepo.deleteById(transactionId);
    }

    @Override
    public TransactionResponse saveTransactionDetails(TransactionRequest transactionRequest) {

        Employee existEmployee = employeeRepo.findByEmployeeId(transactionRequest.getEmployeeId());

        if (transactionRequest.getAdvanceAmount() > 0) {
            transactionRequest.setAdvanceAmount(Math.abs(transactionRequest.getAdvanceAmount()));
            transactionRequest.setDepositAmount(0);
        }
        if (transactionRequest.getDepositAmount() > 0) {
            transactionRequest.setDepositAmount(Math.abs(transactionRequest.getDepositAmount()));
            transactionRequest.setAdvanceAmount(0);
        }
        double workingDays = existEmployee.getWorkingDay();
        double rate = transactionRequest.getRate();
        double messBill = transactionRequest.getMessBill();
        double totalAmount = (workingDays * rate) - messBill + transactionRequest.getDepositAmount();
        double finalPaidAmount = totalAmount - (transactionRequest.getPaidAmount() + transactionRequest.getAccountPaidAmount() + transactionRequest.getAdvanceAmount());
        transactionRequest.setBalanceAmount(finalPaidAmount);

        Transaction transaction =transactionMapper.toEntity(transactionRequest);
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTransactionDate(LocalDate.now());
        TransactionResponse transactionResponse=transactionMapper.toRespone(transaction);
        transactionResponse.setTotalAmount(totalAmount);

        existEmployee.setWorkingDay(0);
        employeeRepo.save(existEmployee);
        transactionRepo.save(transaction);
        return transactionResponse;
    }

    @Override
    public List<TransactionResponse> getTransactionDetails(LocalDate startingDate, LocalDate endingDate) {
        List<Transaction> transactions = transactionCustomRepo.getTransactionDetailByDate(startingDate, endingDate);
        return transactionMapper.toListResponse(transactions);

    }

    @Override
    public Page<TransactionResponse> getTransactionDetailsByPage(LocalDate startingDate, LocalDate endingDate, int page, int size) {
        Page<Transaction> transactionPage = transactionCustomRepo.getTransactionDetailByPage(startingDate, endingDate, page, size);
        List<Transaction> transactionContent = transactionPage.getContent();
        return new PageImpl<>(transactionMapper.toListResponse(transactionContent), transactionPage.getPageable(), transactionPage.getTotalElements());
    }

    @Override
    public List<?> getTotalTransaction(){
        return transactionCustomRepo.getTotalTransaction();
    }

    @Override
    public List<?> getLastTransactionDate(){
        return transactionCustomRepo.getLastTransactionDate();
    }

    @Override
    public List<?> getTotalCalculationTransaction(){
        return transactionCustomRepo.getTotalCalculationTransaction();
    }


}
