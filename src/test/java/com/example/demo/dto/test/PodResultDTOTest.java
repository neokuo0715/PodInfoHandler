package com.example.demo.dto.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dto.PodResultDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
public class PodResultDTOTest {

	// check PodDTO format {uuid,hostName,result,time}
	private ObjectMapper  mapper = new ObjectMapper();
	
	@Test
	void check_PodResultDTO_JSON_format() throws ParseException, JsonProcessingException {
		PodResultDTO dto = new PodResultDTO();
		LocalDate date = LocalDate.of(2022, 9, 24);
		LocalTime time = LocalTime.of(16, 45);
		LocalDateTime dateTime = LocalDateTime.of(date,time);
		dto.setUuid("1234567");
		dto.setHostName("podHal");
		dto.setResult(null);
		dto.setTime(dateTime);
		String json = mapper.writeValueAsString(dto);
		Assertions.assertTrue(json.contains("\"uuid\":\"1234567\""));
		Assertions.assertTrue(json.contains("\"result\":\"\""));
		Assertions.assertTrue(json.contains("\"time\":\"2022-09-24 16:45\""));
		
	}
	
	
}
