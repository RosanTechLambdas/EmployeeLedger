package com.techlambdas.employeeledger.employeeledger.repo;

import com.mongodb.BasicDBObject;
import com.techlambdas.employeeledger.employeeledger.model.Employee;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeFinancialReportResponse;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeResponse;
import com.techlambdas.employeeledger.employeeledger.response.MonthlyReport;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;

@Repository
public class EmployeeCustomRepoImpl implements EmployeeCustomRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Employee> findEmployee(String status, String employeeName, String mobileNo,String keyword) {
        Query query = new Query();
        if(status!=null ){
            query.addCriteria(Criteria.where("status").is(status));
        }
        if (employeeName != null ) {
            query.addCriteria(Criteria.where("employeeName").is(employeeName));
        }
        if (mobileNo != null ) {
            query.addCriteria(Criteria.where("mobileNo").is(mobileNo));
        }
//        query.addCriteria(Criteria.where("status").in("ACTIVE"));
//        query.addCriteria(Criteria.where("status").in("INACTIVE"));

    //        query.addCriteria(Criteria.where("employeeName").regex(".*" + keyword +".*" , "i"));
        query.with(Sort.by(Sort.Direction.ASC, "employeeName"));
        return mongoTemplate.find(query, Employee.class);
    }
    @Override
    public Page<Employee> getEmployee(int page, int size ,String status,String employeeName, String mobileNo){
        Query query = new Query();
        Pageable pageable = PageRequest.of(page,size);
        if(status!=null ){
            query.addCriteria(Criteria.where("status").is(status));
        }
        if (employeeName != null ) {
            query.addCriteria(Criteria.where("employeeName").is(employeeName));
        }
        if (mobileNo != null ) {
            query.addCriteria(Criteria.where("mobileNo").is(mobileNo));
        }
        query.with(pageable);
        List<Employee> employees= mongoTemplate.find(query,Employee.class);
        long count = mongoTemplate.count(query,Employee.class);
        return new PageImpl<>(employees,pageable,count);
    }

    @Override
    public List<?> getEmployeeTransactionDetails() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("status").is("ACTIVE")),

                Aggregation.lookup("transaction", "employeeId", "employeeId", "transactionDetails"),
                Aggregation.unwind("transactionDetails"),

                Aggregation.group("employeeId")
                        .first("employeeName").as("employeeName")
                        .first("status").as("status")
                        .first("mobileNo").as("mobileNo")
                        .first("transactionDetails.transactionId").as("transactionId")
                        .first("transactionDetails.transactionDate").as("transactionDate")
                        .first("transactionDetails.workingDays").as("workingDays")
                        .first("transactionDetails.rate").as("rate")
                        .first("transactionDetails.messBill").as("messBill")
                        .first("transactionDetails.balanceAmount").as("balanceAmount")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                "employee",
                Document.class
        );

        return results.getMappedResults();
    }

    @Override
    public List<?> getEmployeeFinancialReportResponse() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("transaction", "employeeId", "employeeId", "transactionData"),
                Aggregation.unwind("transactionData"),
                Aggregation.group("employeeId")
                        .first("employeeId").as("employeeId")
                        .first("employeeName").as("employeeName")
                        .first("mobileNo").as("employeeMobile")
                        .count().as("totalTransactions")
                        .sum("transactionData.workingDays").as("totalWorkingDays")
                        .avg("transactionData.rate").as("averageRate")
                        .sum("transactionData.messBill").as("totalMessBill")
                        .sum("transactionData.balanceAmount").as("totalBalanceAmount")
                        .sum("transactionData.paidAmount").as("totalPaidAmount")
                        .max("transactionData.transactionDate").as("latestMonth"),
//                        .addToSet(new Document("month", "$transactionData.transactionDate").append("paidAmount", "$transactionData.paidAmount").append("balanceAmount", "$transactionData.balanceAmount")
//                                .append("workingDays", "$transactionData.workingDays")
//                                .append("messBill", "$transactionData.messBill")
//                        ).as("monthlyReport"

       context -> new Document("$replaceRoot",
                            new Document("newRoot",
                                    new Document("employeeId", "$employeeId")
                                            .append("employeeName", "$employeeName")
                                            .append("employeeMobile", "$employeeMobile")
                                            .append("totalBalanceAmount", "$totalBalanceAmount")
                                            .append("totalMessBill", "$totalMessBill")
                                            .append("totalWorkingDays", "$totalWorkingDays")
                                            .append("totalPaidAmount", "$totalPaidAmount")
                                            .append("averageRate", "$averageRate")
                                            .append("totalTransactions", "$totalTransactions")
                                            .append("averageRate", "$averageRate")
                                            .append("monthlyReport", Arrays.asList(
                                                    new Document()
                                                            .append("month", "$latestMonth")
                                                            .append("paidAmount", "$totalPaidAmount")
                                                            .append("balanceAmount", "$totalBalanceAmount")
                                                            .append("workingDays", "$totalWorkingDays")
                                                            .append("messBill", "$totalMessBill")
                                            ))
                            )
                    )
            );

            AggregationResults<EmployeeFinancialReportResponse> results = mongoTemplate.aggregate(
                    aggregation,
                    "employee",
                    EmployeeFinancialReportResponse.class
            );

            return results.getMappedResults();
        }
    }

