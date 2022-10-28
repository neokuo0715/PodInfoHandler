package com.example.demo.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PodDTO_Old {

	private String uuid;
	
	private String podName;
	
	private String status;
	
	private Map<String,String> pods;
	
	private List<PodDTO_Old> podsInfo;
}
