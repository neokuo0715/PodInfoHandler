package com.example.demo.controller.test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.controller.ResultController;
import com.example.demo.dto.PodResultDTO;
import com.example.demo.service.impl.PodResultService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PodResultControllerTest {

	@Autowired
	private ResultController controller;
	
	private PodResultService podService;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@BeforeEach
	void init() {
		podService=Mockito.mock(PodResultService.class);
		
		ReflectionTestUtils.setField(controller, "podService", podService);
	}
	//1.when savePodInfo then call method save PodInfo into file & return String "podInfo saved"
	@Test
	void when_savePodInfo_then_call_service_savePodInfo() {
		//given
		String uuid="1234567";
		String hostName = "podHal";
		//when
		//then
		String result = controller.savePodInfo(new PodResultDTO(uuid, hostName));
		Assertions.assertEquals("podInfo saved", result);
		verify(podService,times(1)).savePodInfo(Mockito.any());
	}
	// 1.1 mock request api
	@Test
	void check_savePodInfo_url() throws URISyntaxException {
		//given
		//when
		//then
		final String baseUrl = "/adapter/podInfo";
        URI uri = new URI(baseUrl);
		String requestBody =  String.format(buildRequetBody(), "") ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(requestBody,headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertEquals("podInfo saved", result.getBody());
	}
	
	
	//2.when savePodInfoWithResult then call method save PodInfo into file & return String "podInfo saved"
	@Test
	void when_savePodInfoWithResult_then_call_savePodInfo() {
		//given
		//when
		String result = controller.savePodInfoWithResult(new PodResultDTO("1234567", "podHal","success"));
		//then
		Assertions.assertEquals("podInfo saved", result);
		verify(podService,times(1)).savePodInfo(Mockito.any());
	}
	
	@Test
	void check_savePodInfoWithResult_url() throws URISyntaxException {
		String url = "/adapter/podInfoWithResult";
		URI uri = new URI(url);
		String json =  String.format(buildRequetBody(), ",\"result\": \"success\"\r\n") ;
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity<>(json,headers);
		ResponseEntity<String> result= restTemplate.postForEntity(uri, request, String.class);
		Assertions.assertEquals("podInfo saved", result.getBody());
	}
	
	
	//3.when getPodInfoByUuid then call method getPodInfoByUuid & return JSON response
	@Test
	void when_getPodInfoByUuid_then_call_getPodInfoByUuid() {
		//given
		//when
		doReturn("jsonStr").when(podService).getPodsInfoByUuid(Mockito.anyString());
		ResponseEntity result = controller.getPodInfoByUuid("1234567");
		//then
		Assertions.assertEquals("jsonStr", result.getBody());
	}
	
	@Test
	void check_getPodInfoByUuid_url() throws URISyntaxException {
		//given
		//when
		String url = "/adapter/podsInfo?uuid=1234567";
		URI uri = new URI(url);
		ResponseEntity<String> jsonStr = restTemplate.getForEntity(uri, String.class);
		//then
		Assertions.assertEquals(200, jsonStr.getStatusCodeValue());
	}
	
	
	private String buildRequetBody() {
		return  "{\r\n"
        		+ "  \"uuid\": \"1234567\",\r\n"
        		+ "  \"hostName\": \"podHal\"\r\n"
        		+"%s"
        		+ "}";
	}
}
