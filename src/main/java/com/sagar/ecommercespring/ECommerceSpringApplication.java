package com.sagar.ecommercespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class ECommerceSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceSpringApplication.class, args);
    }

}
