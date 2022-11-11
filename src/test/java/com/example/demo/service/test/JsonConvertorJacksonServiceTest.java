package com.example.demo.service.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.DemoApplication;
import com.example.demo.dto.PodResultDTO;
import com.example.demo.dto.PodsResultDTO;
import com.example.demo.service.impl.JsonConvertorJacksonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JsonConvertorJacksonServiceTest {

	@Autowired
	private JsonConvertorJacksonService service;
	
	private PodResultDTO podInfo;
	
	private ObjectMapper mapper;
	
	@BeforeEach
	void init() {
		podInfo = new PodResultDTO();
		LocalDateTime now = LocalDateTime.now();
		podInfo.setUuid("1234567");
		podInfo.setHostName("podHal");
		podInfo.setTime(now);
		mapper = Mockito.spy(ObjectMapper.class);
		ReflectionTestUtils.setField(service, "objectMapper", mapper);
	}
	
	/**
	 * used for save into repo
	 * @throws JsonProcessingException 
	 * 
	 */
	@Test
	void when_convertToJsonByPod_then_returnJsonStr() throws JsonProcessingException {
		//given
		String except = getExceptPodStr();
		//when
		String result = service.podConvertToJson(podInfo);
		//then
		Assertions.assertEquals(except, result);
		verify(mapper, times(1)).writeValueAsString(Mockito.any());
	}

	@Test
	void when_convertToJsonByPods_then_returnJsonStr() {
		//given
		List<PodResultDTO> pods = new ArrayList<>();
		pods.add(podInfo);
		String exception =getExceptPodsStr();
		PodsResultDTO podsInfo = new PodsResultDTO();
		podsInfo.setUuid(podInfo.getUuid());
		podsInfo.setPodsInfo(pods);
		//when
		String result = service.podConvertToJson(podsInfo);
		//then
		Assertions.assertEquals(exception, result);
	}
	
	
	@Test
	void when_jsonConvertToPod_then_returnJsonStr() {
		//given
		String podStr = getExceptPodStr();
		//when
		PodResultDTO result = service.jsonConvertToPod(podStr);
		//then
		Assertions.assertEquals(podInfo.getUuid(), result.getUuid());
		Assertions.assertEquals(podInfo.getHostName(), result.getHostName());
		Assertions.assertEquals(podInfo.getResult(), result.getResult());
	}
	
	
	@Test
	void when_convertToPods_then_returnListOfPod() {
		//given
		List<String> podsInfo = new ArrayList<>();
		podsInfo.add(getExceptPodStr());
		//when
		List<PodResultDTO> result = service.convertToPods(podsInfo);
		//then
		Assertions.assertEquals(1, result.size());
	}
	
	
	private String getExceptPodStr() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String time = podInfo.getTime().format(formatter);
		String exception = String.format("{\"uuid\":\"1234567\",\"hostName\":\"podHal\",\"result\":\"\",\"time\":\"%s\"}", time); 
		return exception;
	}
	
	private String getExceptPodsStr() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String time = podInfo.getTime().format(formatter);
		String exception = String.format("{\"uuid\":\"1234567\",\"podsInfo\":[{\"hostName\":\"podHal\",\"result\":\"\",\"time\":\"%s\"}]}", time); 
		return exception;
	}
	
}
