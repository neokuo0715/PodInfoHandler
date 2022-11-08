package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
	
	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendToQueue(String uuid) {
		// TODO Auto-generated method stub
		jmsTemplate.convertAndSend("wut", uuid);
	}

}
