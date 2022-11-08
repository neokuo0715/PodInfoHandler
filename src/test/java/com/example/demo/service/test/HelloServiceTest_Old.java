package com.example.demo.service.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.PodDTO_Old;
import com.example.demo.model.Pod_Old;
import com.example.demo.service.impl.HelloService_Old;

@ExtendWith(MockitoExtension.class)
public class HelloServiceTest_Old {

	@InjectMocks
	private HelloService_Old service;
	
	private PodDTO_Old pod;
	//1.when save pod info ,then save into db (file) and record?
	@BeforeEach
	void init() {
		pod = new PodDTO_Old();
		pod.setUuid("12345678");
		pod.setPodName("podName");
		pod.setStatus("running");
	}
	
	@Test
	void when_savePodInfo_then_write_into_file() throws IOException {
		//given
		//when
		service.savePodInfo(pod);
		//then
		BufferedReader reader = new BufferedReader(new FileReader("./record.txt"));
		StringBuilder stringBuilder = new StringBuilder();
		Assertions.assertThat(reader.lines().map(x->stringBuilder.append(x)).count()).isOne();
		Assertions.assertThat(stringBuilder.toString()).isEqualTo("12345678,podName,running");
		reader.close();
		deleteFile("./record.txt");
		
	}
	@Test
	void when_savePodInfo_into_existedFile() throws IOException {
		//given
		//when
		service.savePodInfo(pod);
		service.savePodInfo(pod);
		
		//then
		BufferedReader reader = new BufferedReader(new FileReader("./record.txt"));
		StringBuilder stringBuilder = new StringBuilder();
		Assertions.assertThat(reader.lines().map(x->stringBuilder.append(x)).count()).isEqualTo(2);
		reader.close();
		deleteFile("./record.txt");
	}
	
	// 
	@Test
	void when_savePodInfo_with_JSON_format() throws IOException {
		//given
		//when
		//then
		service.savePodInfoJSONformat(pod);
		BufferedReader reader = new BufferedReader(new FileReader("./record.txt"));
		StringBuilder stringBuilder = new StringBuilder();
		Assertions.assertThat(reader.lines().map(x->stringBuilder.append(x)).count()).isOne();
		Assertions.assertThat(stringBuilder.toString()).isEqualTo("{\"uuid\":\"12345678\",\"podName\":\"podName\",\"status\":\"running\"}");
		reader.close();
		deleteFile("./record.txt");
		System.out.println(stringBuilder.toString());
	}
	
	@Test
	void when_savePodInfo_with_empty_status_and_JSON_format() throws IOException {
		//given
		pod.setStatus(null);
		//when
		service.savePodInfoJSONformat(pod);
		//then
		BufferedReader reader = new BufferedReader(new FileReader("./record.txt"));
		StringBuilder stringBuilder = new StringBuilder();
		Assertions.assertThat(reader.lines().map(x->stringBuilder.append(x)).count()).isOne();
		Assertions.assertThat(stringBuilder.toString()).isEqualTo("{\"uuid\":\"12345678\",\"podName\":\"podName\"}");
		reader.close();
		deleteFile("./record.txt");
	}
	
	@Test
	void when_getPodInfo_then_return_JSON_response() {
		//given
		pod.setPodName("hal");
		pod.setStatus("success");
		service.savePodInfoJSONformat(pod);
		//when
		//then
		String result = service.getPodInfoWithJSONFormat("12345678");
		Assertions.assertThat(result).isEqualTo("{\"uuid\":\"12345678\",\"podsInfo\":[{\"podName\":\"hal\",\"status\":\"success\"}]}");
		deleteFile("./record.txt");
		
	}
	
	
	
	private void deleteFile(String path) {
		File file = new File(path);
		if (file.delete()) { 
	      System.out.println("Deleted the file: " + file.getName());
	    } else {
	    	File here = new File(".");
	    	System.out.println(here.getAbsolutePath());
	      System.out.println("Failed to delete the file.");
	    } 
	}
	
	
	
}
