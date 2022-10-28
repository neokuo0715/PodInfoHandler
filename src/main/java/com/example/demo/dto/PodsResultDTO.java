package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class PodsResultDTO {

	private String uuid;
	
	private List<PodResultDTO> podsInfo;
	
	public void setPodsInfo(List<PodResultDTO> podsInfo) {

		if(podsInfo.isEmpty()) {
			this.podsInfo = new ArrayList();
		}else {
			this.podsInfo = podsInfo;
		}
	}
	
}
