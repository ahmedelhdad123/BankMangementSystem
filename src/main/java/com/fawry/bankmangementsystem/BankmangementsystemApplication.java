package com.fawry.bankmangementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BankmangementsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankmangementsystemApplication.class, args);
    }

}
