package com.devexperts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.devexperts.service.AccountService;
import com.devexperts.service.AccountServiceImpl;

@EnableAsync
@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
            SpringApplication.run(ApplicationRunner.class, args);
    }

    @Bean
    AccountService accountService() {
        return new AccountServiceImpl();
    }
}
