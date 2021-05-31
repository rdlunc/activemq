package com.rongda.boot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lunrongda
 * @create 2021-05-30 19:41
 */
@SpringBootApplication
@EnableScheduling
public class MainApp_Produce {

    public static void main(String[] args) {
        SpringApplication.run(MainApp_Produce.class, args);
    }

}
