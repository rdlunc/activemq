package com.rongda.activemq.queue;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 消息的消费者
 * @author lunrongda
 * @create 2021-04-13 23:25
 */
@Slf4j
public class JmsConsumer2 {

    public static final String ACTIVEMQ_URL = "tcp://192.168.1.106:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {

        log.info("*********》》》 我是2号消费者");

        //1.创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，第一个是事务 / 第二个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列queue还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        //通过监听的方式来消费消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        log.info("*********》》》 消息者接收消息： " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

        /*
        //同步阻塞方式 [receive()]
        //订阅者或接收者调用 MessageConsumer 的 receive() 方法来接收消息，receive方法在能够接收到消息之前（或超时之前）将一直阻塞。

        while(true){
            //receive()方法是一直等待，“不见不散”。
            //TextMessage textMessage = (TextMessage)messageConsumer.receive();

            //receive(毫秒)方法是等待多少毫秒后，还没有我就走。
            TextMessage textMessage = (TextMessage)messageConsumer.receive(4000L);

            if (null != textMessage){
                log.info("============> 消息者接收消息： " + textMessage.getText());
            } else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();
        */

    }

}
