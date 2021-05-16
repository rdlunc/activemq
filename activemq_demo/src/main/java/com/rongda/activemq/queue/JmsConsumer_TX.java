package com.rongda.activemq.queue;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 消息的消费者
 *
 * 使用场景：
 * 场景1：先生产，只启动1号消费者。
 * 问题：1号消费者能消费消息吗？ 答：能。
 *
 * 场景2：先生产，先启动1号消费者再启动2号消费者。 问题：
 * （1）1号消费者可以消费吗？ 答：能，消费了所有的消息。
 * （2）2号消费者还能消费到消息吗？ 答：不能。
 *
 * 场景3：先启动2个消费者，再生产6条消息。
 * 问题：消费情况是如何？
 * （1）2个消费者都有6条？ 错误
 * （2）先到先得，6条全给一个？ 错误
 * （3）一人一半？ 正确
 *
 * @author lunrongda
 * @create 2021-04-13 23:25
 */
@Slf4j
public class JmsConsumer_TX {

    public static final String ACTIVEMQ_URL = "tcp://192.168.1.106:61616";
    private static final String QUEUE_NAME = "queue_tx01";

    public static void main(String[] args) throws JMSException, IOException {

        log.info("*********》》》 我是1号消费者");

        //1.创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，第一个是事务 / 第二个是签收
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
//        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
//        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地（具体是队列queue还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        //通过监听的方式来消费消息
        /* 异步非阻塞方式 【 监听器onMessage() 】
         * 订阅者或接收者通过 MessageConsumer的setMessageListener(MessageListener listener) 注册一个消息监听器，
         * 当消息到达之后，系统自动调用监听器 MessageListener的onMessage(Message message)方法。
         */
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        log.info("*********》》》 TX消息者接收消息： " + textMessage.getText());
                        log.info("*********》》》 TX消息者接收消息属性： " + textMessage.getBooleanProperty("vip"));

                        textMessage.acknowledge();
                        session.commit();

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
    }

}
