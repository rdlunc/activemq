package com.rongda.activemq.queue;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息的生产者 (队列 queue)
 * @author lunrongda
 * @create 2021-04-12 23:11
 */
@Slf4j
public class JmsProduce {

//    public static final String ACTIVEMQ_URL = "tcp://192.168.1.106:61616";
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
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
        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);

        //生产的消费是否持久化
        //messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT); //非持久化
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT); //持久化 (默认的)

        //6.通过使用messageProducer生产3条消息发送到MQ的队列里面
        for (int i = 1; i <=3 ; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("msg ---> " + i); //可理解为一个字符串
            //设置消息属性（给i是偶数的消息，添加vip销售属性）
            if(i % 2 == 0){
                textMessage.setBooleanProperty("vip", true);
            }
            //8.通过messageProducer发送给 MQ
            messageProducer.send(textMessage);

//            MapMessage mapMessage = session.createMapMessage();
//            mapMessage.setInt("k1", i );
//            messageProducer.send(mapMessage);
        }
        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        log.info("====> 消息发布到MQ完成 " );

    }
}
