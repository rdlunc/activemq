package com.rongda.activemq.topic;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 消息的消费者 (主题 topic)
 * @author lunrongda
 * @create 2021-04-24 18:36
 */
@Slf4j
public class JmsConsumer_Topic {

    public static final String ACTIVEMQ_URL = "tcp://192.168.1.106:61616";
    private static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException, IOException {

        log.info("*********》》》 我是1号消费者");

        //1.创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，第一个是事务 / 第二个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列queue还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);

        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        //通过监听的方式来消费消息
        /*
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        log.info("*********》》》 消息者接收Topic消息： " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        */

        // 使用 Lambda表达式 替换上述写法：
        messageConsumer.setMessageListener((message) -> {
            if (message != null && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage)message;
                try {
                    log.info("*********》》》 消息者接收Topic消息： " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

    }

}
