package com.rongda.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author lunrongda
 * @create 2021-04-13 23:25
 */
public class JmsConsumer {

    public static final String ACTIVEMQ_URL = "tcp://192.168.1.106:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
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
        while(true){
            TextMessage textMessage = (TextMessage)messageConsumer.receive();
            if (null != textMessage){
                System.out.println("============> 消息者接收消息： " + textMessage.getText());
            } else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();
    }

}
