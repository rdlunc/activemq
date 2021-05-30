package com.rongda.activemq;

import com.rongda.boot.activemq.MainApp_Produce;
import com.rongda.boot.activemq.produce.Queue_Produce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.xml.ws.BindingType;

/**
 * @author lunrongda
 * @create 2021-05-31 0:32
 */
@SpringBootTest(classes = MainApp_Produce.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestActiveMQ {

    @Resource
    private Queue_Produce queue_Produce;

    @Test
    public void testSend() throws Exception {
        queue_Produce.produceMsg();
    }

}
