package com.example.demo.controller.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.controller.HelloController_Old;
import com.example.demo.service.impl.HelloService_Old;

@ExtendWith(MockitoExtension.class)
public class HelloControllerTest_Old {

	@InjectMocks
	private HelloController_Old controller;
	
	@Mock
	private HelloService_Old helloService;
	
	
	//1. when pod send uuid 、hostName to adapter ,then save pod's info into file or DB
		// need return ? return type?
	@Test 
	void when_send_Pod_info_then_save_the_podInfo() {
		//given
		//when
		
		controller.savePodInfo("12345678","podName","running");
		//then
		verify(helloService,times(1)).savePodInfoJSONformat(Mockito.any());
	}
	
	@Test
	void when_getPodInfoByuuid_thenReturn_JSON_response() throws IOException {
		
		String JsonResponse = controller.getPodInfoByuuid("12345678");
		verify(helloService,times(1)).getPodInfoWithJSONFormat(Mockito.any());
	}
	
	@Test
	void when_send_Pod_info_withoutStatus_then_save_the_podInfo() {
		//given
		//when
		controller.savePodInfo("1234567", "hal");
		//then
		verify(helloService,times(1)).savePodInfoJSONformat(Mockito.any());
	}
	
	//2. pod send uuid 、hostName、result to adapter,then save pod's info into file or DB
		// need return ? return type?
	//3. Op tools reuqest by uuid ,then adapter return json?
		
}
