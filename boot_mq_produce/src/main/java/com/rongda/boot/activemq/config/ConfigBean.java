package com.rongda.boot.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * @author lunrongda
 * @create 2021-05-30 19:41
 */
@Component
@EnableJms
public class ConfigBean {

    @Value("${myqueue}")
    private String myQueue;

    // <bean id="" class="" />
    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }
}
