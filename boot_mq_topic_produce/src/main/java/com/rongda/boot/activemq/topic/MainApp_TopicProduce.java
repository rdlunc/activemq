package com.rongda.boot.activemq.topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lunrongda
 * @create 2021-05-31 17:10
 */
@SpringBootApplication
@EnableScheduling
public class MainApp_TopicProduce {

    public static void main(String[] args) {
        SpringApplication.run(MainApp_TopicProduce.class, args);
    }

}
