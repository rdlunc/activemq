package com.rongda.boot.activemq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author lunrongda
 * @create 2021-05-31 9:27
 */
@Component
@Slf4j
public class Queue_Consumer {

    @JmsListener(destination = "${myqueue}")
    public void receive(TextMessage textMessage) throws JMSException {
        log.info("=============>>> 消费者收到消息：" + textMessage.getText());
    }

}
