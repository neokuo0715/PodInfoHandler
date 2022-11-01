package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.PodResultDTO;

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
