package com.project.newstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewstartApplication {

    public static void main(String[] args) {

        SpringApplication.run(NewstartApplication.class, args);
    }

}
