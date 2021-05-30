package com.rongda.boot.activemq.produce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

/**
 * @author lunrongda
 * @create 2021-05-30 19:41
 */
@Component
@Slf4j
public class Queue_Produce {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue, "******: " + UUID.randomUUID().toString().substring(0,6));
        log.info("==========>>> produceMsg task is over.");
    }
}
