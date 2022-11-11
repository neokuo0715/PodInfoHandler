package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PodResultDTO;

public interface PodInfoRepoServiceterface {
	
	/**
	 * 儲存pod數據
	 * @param json
	 */
	public void savePodInfoIntoRepo(String json);
	
	/**
	 * 取得 uuid 的 podsInfo
	 * @param uuid
	 * @return
	 */
	public List<String> getPodsInfoByUuid(String uuid) ;
	
}
