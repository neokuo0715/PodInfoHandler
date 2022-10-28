package com.example.demo.dto.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.dto.PodsResultDTO;
import com.example.demo.utils.PodsResultDTOSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@ExtendWith(SpringExtension.class)
public class PodsResultDTOTest {

	private PodsResultDTO dto;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private PodResultDTO podDto;
	
	private List<PodResultDTO> podsInfo;
	@BeforeEach
	void init() {
		podDto = new PodResultDTO();
		podDto.setUuid("1234567");
		podDto.setHostName("podHal");
		podDto.setResult("success");
		podDto.setTime(LocalDateTime.now());
		podsInfo = new ArrayList<>();
		
		podsInfo.add(podDto);
	}
	
	@Test
	void check_JsonFormat_common() throws JsonProcessingException {
		//given
		String uuidJsonPattern = "\"uuid\":\"1234567\"";
		dto = new PodsResultDTO();
		dto.setUuid("1234567");
		dto.setPodsInfo(podsInfo);
		//filter param
		mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(PodsResultDTO.class, new PodsResultDTOSerializer());
		mapper.registerModule(module);
		
		
		//when
		String result1 = mapper.writeValueAsString(dto);
		//then
		Assertions.assertTrue(result1.contains(uuidJsonPattern));
		// podsInfo.notEmpty then 
		Assertions.assertTrue(!result1.contains("\"podsInfo\":[]"));
		Assertions.assertTrue(result1.indexOf(uuidJsonPattern) == result1.lastIndexOf(uuidJsonPattern));
		
	}
	
	@Test
	void check_JsonFormat_noPodsInfo() throws JsonProcessingException {
		//given
		String uuidJsonPattern = "\"uuid\":\"1234567\"";
		dto = new PodsResultDTO();
		dto.setUuid("1234567");
		//when
		String result1 = mapper.writeValueAsString(dto);
		//then
		// podsInfo.isEmpty then []
		dto.setPodsInfo(new ArrayList());
		String result2 = mapper.writeValueAsString(dto);
		Assertions.assertTrue(result2.contains("\"podsInfo\":[]"));
	}
}
