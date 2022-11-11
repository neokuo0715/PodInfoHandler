package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.dto.PodsResultDTO;
import com.example.demo.service.JsonConvertorInterface;
import com.example.demo.utils.PodsResultDTOSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
@Component
@ConditionalOnProperty(value ="remote.adapter.convertor",havingValue ="jackson")
public class JsonConvertorJacksonService implements JsonConvertorInterface {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public String podConvertToJson(Object podInfo) {
		// TODO Auto-generated method stub
		Module module  = buildModule();
		objectMapper.registerModule(module);
		String response = "";
		try {
			response = objectMapper.writeValueAsString(podInfo);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public PodResultDTO jsonConvertToPod(String podStr)  {
		// TODO Auto-generated method stub
		PodResultDTO result = null;
		objectMapper.registerModule(new JavaTimeModule());
		try {
			result = objectMapper.readValue(podStr, PodResultDTO.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<PodResultDTO> convertToPods(List<String> podsInfo) {
		// TODO Auto-generated method stub
		return podsInfo.stream().map(pod -> jsonConvertToPod(pod)).collect(Collectors.toList());
	}
	

	
	private Module buildModule() {
		// set custom serializer 
		SimpleModule module = new SimpleModule();
		module.addSerializer(PodsResultDTO.class, new PodsResultDTOSerializer());
		return module;
	}


	
	
}
