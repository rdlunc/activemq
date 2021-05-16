package com.rongda.activemq.topic;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 持久化消息的消费者 (主题 topic)
 * 1.一定要先运行一次消费者，等于向MQ注册，类似我订阅了这个主题；
 * 2.然后再运行生产者发送消息；
 * 3.此时，无论消费者是否在线，都会接收到，不在线的话，下次连接的时候，会把没有收到的消息都接收下来。
 *
 * @author lunrongda
 * @create 2021-04-24 18:36
 */
@Slf4j
public class JmsConsumer_Topic_Persist {

    public static final String ACTIVEMQ_URL = "tcp://192.168.1.106:61616";
    private static final String TOPIC_NAME = "topic-Persist";

    public static void main(String[] args) throws JMSException, IOException {

        log.info("*********》》》 我是1号消费者");

        //1.创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("ConsumerNO1");

        //3.创建会话session
        //两个参数，第一个是事务 / 第二个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列queue还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "Remark...");
        connection.start();

        Message message = topicSubscriber.receive();
        while(null != message){
            TextMessage textMessage = (TextMessage)message;
            log.info("************>>> 收到的持久化topic： " + textMessage.getText());
            message = topicSubscriber.receive();
        }
        session.close();
        connection.close();

    }

}
