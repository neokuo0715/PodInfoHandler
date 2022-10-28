package com.example.demo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PodDTO_Old;
import com.example.demo.model.Pod_Old;
import com.google.gson.Gson;

@Service
public class HelloService_Old {

	private String localFilePath = "./record.txt";
	
	/**
	 * pod 資訊以一般string儲存
	 * @param pod
	 * @deprecated
	 */
	public void savePodInfo(PodDTO_Old pod) {
		// createFile
		File file = createFile(localFilePath);
		StringBuilder strBuilder = buildPodInfoContent(pod);
		writePodInfoIntoFile(strBuilder.toString());
	}
	
	/**
	 * 將pod資以json方式儲存
	 * @param pod
	 */
	public void savePodInfoJSONformat(PodDTO_Old pod) {
		File file = createFile(localFilePath);
		String podJSON = new Gson().toJson(pod);
		writePodInfoIntoFile(podJSON);
	}
	
	/**
	 * 以JSON方式回傳PodsInfo
	 * @param uuid
	 * @return
	 */
	public String getPodInfoWithJSONFormat(String uuid) {
		//get List Pods
		//build responseJSON format
		// return JSON
		List<Pod_Old> pods = getPodInfoFromFile(localFilePath);
		List<PodDTO_Old> podsInfo = pods.stream().filter(x->uuid.equals(x.getUuid())).map(pod -> {
			PodDTO_Old podDto = new PodDTO_Old();
			podDto.setPodName(pod.getPodName());
			podDto.setStatus(pod.getStatus());
			return podDto;
		}).collect(Collectors.toList());
		PodDTO_Old podDto = new PodDTO_Old();
		podDto.setPodsInfo(podsInfo);
		podDto.setUuid(uuid);
		return new Gson().toJson(podDto);
	}
	
	
	
	/**
	 * 建立檔案
	 * @param path 檔案路徑名稱ex:"./test.txt"
	 * @return File
	 */
	public File createFile(String path) {
		File myObj = new File(path);
		
		try {
	      if (myObj.createNewFile()) {
	        System.out.println("File created: " + myObj.getName());
	      } else {
	        System.out.println("File already exists.");
	      }
	      
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		return myObj;
	}
	
	/**
	 * 以StringBuilder 方式儲存pod資訊
	 * @param pod
	 * @return StringBuilder
	 * @deprecated
	 */
	public StringBuilder buildPodInfoContent (PodDTO_Old pod) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(pod.getUuid()+",");
		strBuilder.append(pod.getPodName()+",");
		strBuilder.append(pod.getStatus());
		return strBuilder;
	}
	
	/**
	 * 將建立好的pod資訊存入file當中
	 * @param strBuilder
	 */
	public void writePodInfoIntoFile(String strBuilder) {
		try {
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(localFilePath,true));
			System.out.println(strBuilder.toString());
			if(isFileHasContent(localFilePath)) {
				output.newLine();
			}
			output.append(strBuilder.toString());
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 檔案裡面是否有內容
	 * @param filePath
	 * @return boolean
	 */
	public boolean isFileHasContent(String filePath)  {
		boolean result = false;
		try {
			BufferedReader fr = new BufferedReader(new FileReader(filePath));
			result = fr.readLine() != null;
			fr.close();
		} catch (IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 從檔案取得pod資訊
	 * @param filePath
	 * @return {@code List<Pod>} 
	 */
	public List<Pod_Old> getPodInfoFromFile(String filePath) {
		List<Pod_Old> results = new ArrayList();
		try {
			BufferedReader fr = new BufferedReader(new FileReader(filePath));
			fr.lines().forEach(p -> results.add(new Gson().fromJson(p,Pod_Old.class)));
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
}
