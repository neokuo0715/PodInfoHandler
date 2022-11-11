package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.dto.PodsResultDTO;
import com.example.demo.service.JsonConvertorInterface;
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
	
	@Autowired
	private JsonConvertorInterface jsonConvertor;
	
 	/**
 	 * 為了之後取得這些Pods資訊
 	 * @param podDto
 	 */
	public void savePodInfo(PodResultDTO podDto) {
		podDto.setTime(LocalDateTime.now());
		String podInfoJson = jsonConvertor.podConvertToJson(podDto);
		podInfoRepoSvc.savePodInfoIntoRepo(podInfoJson);
		
	}
	
	/**
	 * 
	 * this response is used for diagrams
	 * @param uuid
	 * @return String
	 */
	public String getPodsInfoByUuid(String uuid)  {
		
		List<String> podsInfoStr =  podInfoRepoSvc.getPodsInfoByUuid(uuid);
		List<PodResultDTO> podsInfo = jsonConvertor.convertToPods(podsInfoStr);
		//find file 
		PodsResultDTO result = new PodsResultDTO();
		result.setUuid(uuid);
		result.setPodsInfo(podsInfo);
		return jsonConvertor.podConvertToJson(result);
	}
}
