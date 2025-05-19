package com.techlambdas.employeeledger.employeeledger.repo;

import com.techlambdas.employeeledger.employeeledger.model.Employee;
import com.techlambdas.employeeledger.employeeledger.model.Transaction;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.*;
import java.util.List;

@Repository
public class TransactionRepoImpl implements TransactionCustomRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Transaction> getTransactionDetailByDate(LocalDate startingDate, LocalDate endingDate) {
        Query query = new Query();
        if(startingDate!=null && endingDate!=null) {
            query.addCriteria(Criteria.where("transactionDate")
                    .gte(startingDate)
                    .lte(endingDate));

        }
//        query.addCriteria(Criteria.where("balanceAmount").gt(0));  //task-1
        query.addCriteria(Criteria.where("workingDays").gte(20)); //task-6
        query.addCriteria(Criteria.where("messBill").lte(1500));//task-6
        query.addCriteria(Criteria.where("accountPaidAmount").gt(0));//task-6
        query.addCriteria(Criteria.where("paidAmount").exists(true)); //task-3
        return mongoTemplate.find(query, Transaction.class);
    }

    @Override
    public Page<Transaction> getTransactionDetailByPage(LocalDate startingDate, LocalDate endingDate,int page,int siza){
        Query query = new Query();
        if(startingDate!=null && endingDate!=null) {
            query.addCriteria(Criteria.where("transactionDate")
                    .gte(startingDate)
                    .lte(endingDate));
        }
        Pageable pageable = PageRequest.of(page,siza);
        query.with(pageable);
        List<Transaction> transactions= mongoTemplate.find(query,Transaction.class);
        long count = mongoTemplate.count(query,Transaction.class);
        return new PageImpl<>(transactions,pageable,count);
    }

    @Override
    public List<Document> getTotalTransaction() {                //task-2
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("employeeId")
                        .count().as("totalTransactions")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                "transaction",
                Document.class
        );

        return results.getMappedResults();
    }
    @Override
    public List<Document> getLastTransactionDate() {                //task-5
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("employeeId")
                        .max("transactionDate").as("lastTransactionDate")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                "transaction",
                Document.class
        );

        return results.getMappedResults();
    }

    @Override
    public List<?> getTotalCalculationTransaction() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("employee", "employeeId", "employeeId", "employeeInfo"),
                Aggregation.unwind("employeeInfo"),
                Aggregation.group("employeeId")
                        .count().as("totalTransactions")
                        .sum("balanceAmount").as("totalBalanceAmount")
                        .sum("paidAmount").as("totalPaidAmount")
                        .sum("advanceAmount").as("totalAdvanceAmount")
                        .sum("messBill").as("totalMessBill")
                        .sum("workingDays").as("totalWorkingDays")
                        .sum("rate").as("totalRate")
                        .first("employeeInfo.employeeName").as("employeeName")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                "transaction",
                Document.class
        );

        return results.getMappedResults();
    }
}

