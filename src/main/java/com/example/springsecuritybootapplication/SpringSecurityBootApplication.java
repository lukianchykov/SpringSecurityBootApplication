package com.example.springsecuritybootapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = "com.example.springsecuritybootapplication")
@ImportResource("classpath:Security.xml")
public class SpringSecurityBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityBootApplication.class, args);
    }

}
