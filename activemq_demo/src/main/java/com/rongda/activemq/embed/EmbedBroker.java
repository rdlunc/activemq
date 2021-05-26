package com.rongda.activemq.embed;

import org.apache.activemq.broker.BrokerService;

/**
 * ActiveMQ也支持在VM中通信基于嵌入式的broker
 * @author lunrongda
 * @create 2021-05-16 23:39
 */
public class EmbedBroker {

    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }

}
