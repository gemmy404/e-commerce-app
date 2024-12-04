package com.app.furniture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FurnitureApp {

    public static void main(String[] args) {
        SpringApplication.run(FurnitureApp.class, args);
    }

}
