package com.recipeshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Main Entry Point for the Online Recipe Sharing Platform Spring Boot Application.
 */
@ServletComponentScan
@SpringBootApplication
public class RecipeSharingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeSharingApplication.class, args);
    }
}
