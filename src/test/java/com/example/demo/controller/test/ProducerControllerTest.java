package com.example.demo.controller.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.controller.ProducerController;
import com.example.demo.service.impl.ProducerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProducerControllerTest {
	
	@Autowired
	private ProducerController controller;
	
	private ProducerService producerService;
	
	@BeforeEach
	void init() {
		producerService = Mockito.mock(ProducerService.class);
		ReflectionTestUtils.setField(controller, "service", producerService);
	}
	
	/**
	 * 
	 */
	@Test
	void when_provideUuidForQueue_then_pass_uuid() {
		//given
		//when
		controller.provideUuidForQueue("1234567");
		//then
		verify(producerService, times(1)).sendToQueue(Mockito.any());
	}
}
