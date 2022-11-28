package com.example.demo.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.apache.activemq.artemis.junit.EmbeddedActiveMQResource;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.DemoApplication;
import com.example.demo.service.impl.ProducerService;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProducerServiceTest {

	@Autowired
	private ProducerService service;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Rule
	public EmbeddedActiveMQResource resource = new EmbeddedActiveMQResource();
    
	private MockWebServer mockServer;
	
	
	@BeforeEach
	void init() throws IOException {
		mockServer = new MockWebServer();
		mockServer.start(8765);
		mockServer.setDispatcher(buildMockResponse());
		ReflectionTestUtils.setField(service, "mordermtearUrl", "http://localhost:8765/");
	}
	
	@AfterEach
	void end() throws IOException {
		mockServer.shutdown();
	}
	
	@Test
	void whenSendingMessage_thenCorrectQueueAndMessageText() throws Exception {
		service.sendToQueue("1234567");
		Object output = jmsTemplate.receiveAndConvert("wut");
		Assertions.assertEquals("1234567", output);
	}
	
	@Test
	void whenSendMessage_thenCallDemoApp() throws IOException {
		//given
		//when
		//then
		service.sendToQueue("1234567");
		Object output = jmsTemplate.receiveAndConvert("wut");
		Assertions.assertEquals("1234567", output);
	}
	
	@Test
	void whenSendMessage_ifStatusNotEqualToSuccess_thenThrowError() {
		//given
		ReflectionTestUtils.setField(service, "url", "/demo/testFail");
		//when
		
		//then
		assertThrows(WebClientResponseException.class, () -> service.sendToQueue("1234567"));
		ReflectionTestUtils.setField(service, "url", "/demo/test");
	}
	
	private Dispatcher buildMockResponse() {
		return new Dispatcher() {
	    @Override
	    public MockResponse dispatch(RecordedRequest request) {
	    	System.out.println("request.getPath()"+request.getPath());
	      switch (request.getPath()) {
	        case "/demo/test":
	          return new MockResponse().setResponseCode(200).setBody("OKTest");
	        case "/demo/testFail":
	          return new MockResponse().setResponseCode(405);
	      }
	      return new MockResponse().setResponseCode(404);
	    }
	  };
	}
	
}