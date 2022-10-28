package com.example.demo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.dto.PodsResultDTO;
import com.example.demo.utils.PodsResultDTOSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Service
public class PodResultService {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${result.api.position}")
	private String podsInfoPosition;
 	
	public void savePodInfo(PodResultDTO podDto) {
		podDto.setTime(LocalDateTime.now());
		String podInfoJson = converToJson(podDto);
		writeStrIntoFile(podInfoJson);
		
	}
	
	public String getPodsInfoByUuid(String uuid)  {
		
		//find file 
		File file = new File(podsInfoPosition);
		PodsResultDTO podsResult = new PodsResultDTO();
		String result="";
		if(file.exists()) {
			// set to PodsInfoDTO
			List<PodResultDTO> podsInfo = savePodsInfoIntoList(file);
			podsResult.setUuid(uuid);
			podsResult.setPodsInfo(podsInfo);
			// converto JSON
			objectMapper.registerModule(buildModule());
			try {
				result = objectMapper.writeValueAsString(podsResult);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			// do not exist
			result = "no file detected";
		}
		return result;
	}

	private String converToJson(PodResultDTO podDto) {
		//jackson
		String result ="";
		try {
			result = objectMapper.writeValueAsString(podDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}


	private void writeStrIntoFile(String podInfoJson) {
		try {
			File file = new File(podsInfoPosition);
			file.createNewFile();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
			if(!isFileNoContent(file)) bw.append(System.lineSeparator());
			bw.append(podInfoJson);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private boolean isFileNoContent(File file) throws IOException {
		boolean result = false;
		BufferedReader br = new BufferedReader(new FileReader(file));     
		result = br.readLine() == null;
		br.close();
		return result;
		
	}

	private List<PodResultDTO> savePodsInfoIntoList(File file){
		List<PodResultDTO> result = new ArrayList<>();
		// prase file to obj.
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while(br.ready()) {
				PodResultDTO podDto = objectMapper.readValue(br.readLine(), PodResultDTO.class);
				result.add(podDto);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	private Module buildModule() {
		// set custom serializer 
		SimpleModule module = new SimpleModule();
		module.addSerializer(PodsResultDTO.class, new PodsResultDTOSerializer());
		return module;
	}
}
