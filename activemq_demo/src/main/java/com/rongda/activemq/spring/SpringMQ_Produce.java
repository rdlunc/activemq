package com.rongda.activemq.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author lunrongda
 * @create 2021-05-26 23:21
 */
@Slf4j
@Service
public class SpringMQ_Produce {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Produce produce = (SpringMQ_Produce)ctx.getBean("springMQ_Produce");

//        produce.jmsTemplate.send(new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                TextMessage textMessage = session.createTextMessage("***************>>> Spring和ActiveMQ的整合Case. ");
//                return textMessage;
//            }
//        });

        produce.jmsTemplate.send((session) -> {
            TextMessage textMessage = session.createTextMessage("***************>>> Spring和ActiveMQ的整合Case. ");
            return textMessage;
        });

        log.info("=================>>> Send task over.");
    }
}
