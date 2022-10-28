package com.exmaple.demo.service.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.service.PodResultService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PodResultServiceTest {
	
	@Autowired
	private PodResultService service ;
	
	
	private PodResultDTO podDto;
	
	@Value("${result.api.position}")
	private String filePostion;
	//1.when savePodInfo(PodResultDTO) then write podInfo into file 
	@BeforeEach
	void init() {
		
		podDto = new PodResultDTO();
		podDto.setUuid("1234567");
		podDto.setHostName("podHal");
		podDto.setResult("success");
	}
	
	@Test
	void when_savePodInfo_then_write_into_file() throws IOException {
		//given
		LocalDateTime dateTime = LocalDateTime.now(); 
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String json = String.format("{\"uuid\":\"1234567\",\"hostName\":\"podHal\",\"result\":\"success\",\"time\":\"%s\"}",dateTime.format(format));
		//when
		
		//then
		service.savePodInfo(podDto);

		StringBuilder strBuilder = new StringBuilder();
		long lineCount = getContentAndlinesFromFile(filePostion,strBuilder);
		Assertions.assertEquals(1, lineCount);
		Assertions.assertEquals(json, strBuilder.toString());
		new File(filePostion).delete();
	}
	@Test
	void when_savePodInfoWithoutResult_then_write_into_file() throws IOException {
		//given
		LocalDateTime dateTime = LocalDateTime.now(); 
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String json = String.format("{\"uuid\":\"1234567\",\"hostName\":\"podHal\",\"result\":\"\",\"time\":\"%s\"}",dateTime.format(format));
		podDto.setResult(null);
		//when
		
		//then
		service.savePodInfo(podDto);
		StringBuilder strBuilder = new StringBuilder();
		long lineCount = getContentAndlinesFromFile(filePostion,strBuilder);
		
		Assertions.assertEquals(1, lineCount);
		Assertions.assertEquals(json, strBuilder.toString());
		new File(filePostion).delete();
	}
	
	@Test
	void when_savePodInfo_In_existedFile_then_write_nextLine() {
		//given
		service.savePodInfo(podDto);
		service.savePodInfo(podDto);
		
		//when
		//then
		StringBuilder strBuilder = new StringBuilder();
		long lineCount = getContentAndlinesFromFile(filePostion,strBuilder);
		Assertions.assertEquals(2, lineCount);
		new File(filePostion).delete();
	}
	
	//3.when getPodInfoByUuid(String uuid) then return JOSN　response
			// JSON format {uuid:"" ,pods[{hostName:"",result:"",time:""}]}
	@Test
	void when_getPodInfoByUuid_then_return_JsonRes() {
		//given
		service.savePodInfo(podDto);
		
		//when
		//then
		String result = service.getPodsInfoByUuid("1234567");
		// has uuid:1234567
		// podsInfo : []
		Assertions.assertTrue(result.contains("\"uuid\":\"1234567\""));
		new File(filePostion).delete();
	}
	
	@Test
	void when_getPodsinfoByUuidWithMulti_then_return_JsonRes() {
		//given
		service.savePodInfo(podDto);
		service.savePodInfo(new PodResultDTO("1234567","podEven"));
		//when
		
		//then
		String result = service.getPodsInfoByUuid("1234567");
		Assertions.assertTrue(result.contains("podHal"));
		Assertions.assertTrue(result.contains("podEven"));
		new File(filePostion).delete();
	}
	
	
	private long getContentAndlinesFromFile(String path, StringBuilder strBuilder) {
		File file = new File(path);
		long lineCount = 0;
		try {
			file.createNewFile();
			 BufferedReader bfr = new BufferedReader(new FileReader(file));
			 lineCount = bfr.lines().map(line -> {
				if(!strBuilder.toString().equals("")) {
					strBuilder.append(System.lineSeparator());
					strBuilder.append(line);
				}else {
					strBuilder.append(line);
				}
				return "";
			}).count();
			bfr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lineCount;
	}
	
	
	
	
	
}
