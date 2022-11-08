package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.impl.ProducerService;

@RestController
@RequestMapping("/producer")
public class ProducerController {

	
	@Autowired
	private ProducerService service;
	
	/**
	 * provide uuid to Queue for memory reload mechanism
	 * @param uuid
	 * @return
	 */
	@GetMapping("/{uuid}")
	public void provideUuidForQueue(@PathVariable("uuid") String uuid) {
		// 1.get uuid or generate uuid
		// 2.request to queue
		service.sendToQueue(uuid);
		
	}
}
