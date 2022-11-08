package com.example.demo.service.test;

import org.apache.activemq.artemis.junit.EmbeddedActiveMQResource;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.DemoApplication;
import com.example.demo.service.impl.ProducerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProducerServiceTest {

	@Autowired
	private ProducerService service;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Rule
	public EmbeddedActiveMQResource resource = new EmbeddedActiveMQResource();
    
	
	
	@Test
	public void whenSendingMessage_thenCorrectQueueAndMessageText() throws Exception {
		service.sendToQueue("1234567");
		Object output = jmsTemplate.receiveAndConvert("wut");
		Assertions.assertEquals("1234567", output);
	}
	
}