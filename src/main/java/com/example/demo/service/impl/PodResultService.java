package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.dto.PodsResultDTO;
import com.example.demo.service.PodInfoRepoServiceterface;
import com.example.demo.utils.PodsResultDTOSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Service
public class PodResultService {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PodInfoRepoServiceterface podInfoRepoSvc;
	
	
	
 	/**
 	 * 為了之後取得這些Pods資訊
 	 * @param podDto
 	 */
	public void savePodInfo(PodResultDTO podDto) {
		podDto.setTime(LocalDateTime.now());
		String podInfoJson = convertToJson(podDto);
		podInfoRepoSvc.savePodInfoIntoRepo(podInfoJson);
		
	}
	
	/**
	 * 
	 * this response is used for diagrams
	 * @param uuid
	 * @return String
	 */
	public String getPodsInfoByUuid(String uuid)  {
		
		List<PodResultDTO> podsInfo =  podInfoRepoSvc.getPodsInfoByUuid(uuid);
		//find file 
		PodsResultDTO result = new PodsResultDTO();
		result.setUuid(uuid);
		result.setPodsInfo(podsInfo);
		return convertToJson(result);
	}

	
	private String convertToJson(Object result) {
		//jackson
		Module module  = buildModule();
		objectMapper.registerModule(module);
		String response = "";
		try {
			response = objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	
	private Module buildModule() {
		// set custom serializer 
		SimpleModule module = new SimpleModule();
		module.addSerializer(PodsResultDTO.class, new PodsResultDTOSerializer());
		return module;
	}
}
