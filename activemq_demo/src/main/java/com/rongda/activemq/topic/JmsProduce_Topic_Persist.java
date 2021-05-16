package com.rongda.activemq.topic;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 持久化消息的生产者 (主题 topic)
 * @author lunrongda
 * @create 2021-04-24 18:24
 */
@Slf4j
public class JmsProduce_Topic_Persist {
    public static final String ACTIVEMQ_URL = "tcp://192.168.1.106:61616";
    private static final String TOPIC_NAME = "topic-Persist";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        //3.创建会话session
        //两个参数，第一个是事务 / 第二个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列queue还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();

        //6.通过使用messageProducer生产3条消息发送到MQ的队列里面
        for (int i = 1; i <=3 ; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("msg-persist ---> " + i); //可理解为一个字符串
            //8.通过messageProducer发送给 MQ
            messageProducer.send(textMessage);
        }
        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        log.info("====> 带持久化的Topic消息发布到MQ完成 " );

    }
}
