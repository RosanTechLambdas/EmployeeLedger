package com.techlambdas.employeeledger.employeeledger.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingService {

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 22 11 * * *")
    public void scheduleEmail() {
        emailService.sendEmail(
                "rosannatarajan.n@gmail.com",
                "hello"
                , "hello world"
        );
        System.out.println("sending email");
    }
}
