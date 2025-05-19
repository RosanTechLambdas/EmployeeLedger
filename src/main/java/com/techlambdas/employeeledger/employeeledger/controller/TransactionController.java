package com.techlambdas.employeeledger.employeeledger.controller;

import com.techlambdas.employeeledger.employeeledger.request.TransactionRequest;
import com.techlambdas.employeeledger.employeeledger.response.AppResponse;
import com.techlambdas.employeeledger.employeeledger.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("transaction")
public class TransactionController
{

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<?> getTransaction(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endingDate) {
        Map<String, Object> response = new HashMap<>();
        response.put("transactions", transactionService.getTransactionDetails(startingDate, endingDate));
        return AppResponse.successResponse(HttpStatus.OK, response);
    }

    @GetMapping("{transactionId}")
    public ResponseEntity<?> getTransactionDetailsByEmployeeId(@PathVariable String transactionId) {
        Map<String, Object> response = new HashMap<>();
        response.put("transaction", transactionService.getTransactionDetailsByEmployeeId(transactionId));
        return AppResponse.successResponse(HttpStatus.OK, response);
    }

    @PostMapping
    public ResponseEntity<?> saveTransactionDetails(@RequestBody TransactionRequest transactionRequest) {
        Map<String, Object> response = new HashMap<>();
        response.put("transaction", transactionService.saveTransactionDetails(transactionRequest));
        return AppResponse.successResponse(HttpStatus.CREATED, response);
    }

    @PutMapping("{transactionId}")
    public ResponseEntity<?> updateTransactionDetailsByEmployeeId(@RequestBody TransactionRequest transactionRequest, @PathVariable String employeeId) {
        Map<String, Object> response = new HashMap<>();
        response.put("transaction", transactionService.updateTransactionDetailsByEmployeeId(transactionRequest, employeeId));
        return AppResponse.successResponse(HttpStatus.OK, response);
    }

    @DeleteMapping("{transactionId}")
    public ResponseEntity<?> deleteTransactionDetailsByEmployeeId(@PathVariable String transactionId) {
        transactionService.deleteTransactionDetailsByEmployeeId(transactionId);
        Map<String, Object> response = new HashMap<>();
        response.put("transaction", "transaction deleted");
        return AppResponse.successResponse(HttpStatus.OK, response);
    }

    @GetMapping("page")
    public ResponseEntity<?> getTransactionPage(@RequestParam(required = false) int page,
                                                @RequestParam(required = false) int size,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate  endingDate) {
        System.out.println("starting date "+startingDate+" ending date "+endingDate);
        Map<String, Object> response = new HashMap<>();
        response.put("transactions", transactionService.getTransactionDetailsByPage(startingDate, endingDate, page, size));
        return AppResponse.successResponse(HttpStatus.OK, response);
    }
    @GetMapping("/getTotalTransaction")
    public ResponseEntity<?> getTotalTransaction(){
        Map<String, Object> response = new HashMap<>();
        response.put("message",transactionService.getTotalTransaction());
        return AppResponse.successResponse(HttpStatus.OK,response);
    }

    @GetMapping("/getLastTransactionDate")
    public ResponseEntity<?> getLastTransactionDate(){
        Map<String, Object> response = new HashMap<>();
        response.put("message",transactionService.getLastTransactionDate());
        return AppResponse.successResponse(HttpStatus.OK,response);
    }

    @GetMapping("/getTotalCalculationTransaction")
    public ResponseEntity<?> getTotalCalculationTransaction(){
         Map<String, Object> response = new HashMap<>();
         response.put("message",transactionService.getTotalCalculationTransaction());
         return AppResponse.successResponse(HttpStatus.OK,response);
    }
}
