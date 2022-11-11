package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.dto.PodsResultDTO;

public interface JsonConvertorInterface {

	public String podConvertToJson(Object pod);
	
	
	public List<PodResultDTO> convertToPods(List<String> podsInfo);
	
	public PodResultDTO jsonConvertToPod(String podStr);
}
