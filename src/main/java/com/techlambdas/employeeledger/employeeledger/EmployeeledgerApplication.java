package com.techlambdas.employeeledger.employeeledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableMongoAuditing(auditorAwareRef = "auditorAware")
@EnableScheduling
@EnableTransactionManagement
public class EmployeeledgerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeledgerApplication.class, args);
	}

}
