package com.rongda.boot.activemq.topic.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author lunrongda
 * @create 2021-05-31 17:53
 */
@Component
@Slf4j
public class Topic_Consumer {

    @JmsListener(destination = "${mytopic}")
    public void receive(TextMessage textMessage) throws JMSException {
        log.info("消费者收到订阅的主题： " + textMessage.getText());

    }

}
