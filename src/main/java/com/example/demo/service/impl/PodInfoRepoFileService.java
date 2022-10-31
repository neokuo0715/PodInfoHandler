package com.example.demo.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.service.PodInfoRepoServiceterface;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@ConditionalOnProperty(value ="remote.adapter.podInfoRepo",havingValue ="localFile")
public class PodInfoRepoFileService implements PodInfoRepoServiceterface {

	@Value("${remote.adapter.filePath}")
	private String podsInfoPosition;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public void savePodInfoIntoRepo(String podInfoJson) {
		try {
			File file = new File(podsInfoPosition);
			file.createNewFile();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
			if(!isFileContentEmpty(file)) bw.append(System.lineSeparator());
			bw.append(podInfoJson);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public List<PodResultDTO> getPodsInfoByUuid(String uuid) {
		// TODO Auto-generated method stub
		File file = new File(podsInfoPosition);
		if(file.exists()) {
			List<PodResultDTO> result = new ArrayList<>();
			// prase file to obj.
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				while(br.ready()) {
					String podInfo = br.readLine();
					// if line has nothing
					if(podInfo.trim().length() != 0) {
						PodResultDTO podDto = objectMapper.readValue(podInfo, PodResultDTO.class);
						result.add(podDto);
					}
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result.stream().filter(podInfo -> podInfo.getUuid().equals(uuid)).collect(Collectors.toList());
		}else {
			return null;
		}
		
	}

	private boolean isFileContentEmpty(File file) throws IOException {
		boolean result = false;
		BufferedReader br = new BufferedReader(new FileReader(file));
		result = br.readLine() == null;
		br.close();
		return result;
		
	}
	
	
}
