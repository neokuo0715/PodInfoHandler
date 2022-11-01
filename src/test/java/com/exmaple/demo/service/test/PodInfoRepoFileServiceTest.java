package com.exmaple.demo.service.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.service.impl.PodInfoRepoFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(SpringExtension.class)
public class PodInfoRepoFileServiceTest {
	
	@InjectMocks
	private PodInfoRepoFileService service;
	
	@Spy
	private ObjectMapper mapper;
	
	@Value("podsInfo.txt")
	private String filePath;

	
	@BeforeEach
	void init() {
		mapper.registerModule(new JavaTimeModule());
		ReflectionTestUtils.setField(service, "objectMapper", mapper);
		ReflectionTestUtils.setField(service, "podsInfoPosition", filePath);
		
	}
	
	@Test
	void when_getPodsInfoByUuid_then_return_filteredResult() throws IOException {
		// given
		// add fake info
		File file = new File(filePath);
		file.createNewFile();
		BufferedWriter fw = new BufferedWriter(new FileWriter(filePath));
		fw.append(fakePodInfo());
		fw.close();
		//when
		List<PodResultDTO>  result = service.getPodsInfoByUuid("1234566");
		//then
		Assertions.assertTrue(result.isEmpty());
		file.delete();
		
	}
	
	@Test
	void checkContent_onlyFirstLineIsEmpty() throws IOException {
		//given
		File file = new File(filePath);
		file.createNewFile();
		BufferedWriter fw = new BufferedWriter(new FileWriter(filePath,true));
		fw.append(System.lineSeparator());
		fw.append(fakePodInfo());
		fw.close();
		//when
		List<PodResultDTO>  result = service.getPodsInfoByUuid("1234567");
		//then
		Assertions.assertEquals(1, result.size());
		file.delete();
	}
	
	
	private String fakePodInfo() {
		LocalDateTime dateTime = LocalDateTime.now(); 
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String json = String.format("{\"uuid\":\"1234567\",\"hostName\":\"podHal\",\"result\":\"success\",\"time\":\"%s\"}",dateTime.format(format));
		return json;
	}
	
}
