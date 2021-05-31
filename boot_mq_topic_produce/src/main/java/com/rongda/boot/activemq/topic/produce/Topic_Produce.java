package com.rongda.boot.activemq.topic.produce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.UUID;

/**
 * @author lunrongda
 * @create 2021-05-31 17:11
 */
@Component
@Slf4j
public class Topic_Produce {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;

    /**
     * 间隔时间3秒钟定投
     */
    @Scheduled(fixedDelay = 3000)
    public void produceTopicMsgScheduled() {
        jmsMessagingTemplate.convertAndSend(topic, "主题消息： " + UUID.randomUUID().toString().substring(0, 6));
        log.info("==========>>> Scheduled produceTopicMsg task is over.");
    }

}
