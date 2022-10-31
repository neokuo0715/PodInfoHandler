package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.service.impl.PodResultService;



@Controller
@RequestMapping("/adapter")
@ResponseBody
public class ResultController {

	@Autowired
	private PodResultService podService;

	/**
	 * 在memory reload 前pod資訊
	 * @param podInfoDto
	 * @return
	 */
	@PostMapping(value="/podInfo",consumes = "application/json")
	public String savePodInfo(@RequestBody PodResultDTO podInfoDto) {
		podService.savePodInfo(podInfoDto);
		return "podInfo saved";
	}

	/**
	 * memory reload 後pod 結果
	 * @param podInfoDto
	 * @return
	 */
	@PostMapping(value="/podInfoWithResult",consumes = "application/json",produces = "application/json")
	public String savePodInfoWithResult(@RequestBody PodResultDTO podInfoDto) {
		podService.savePodInfo(podInfoDto);
		return "podInfo saved";
	}

	/**
	 * 用於圖表繪製的資料依據
	 * @param  uuid
	 * @return {@code ResponseEntity<String>}
	 */
	@GetMapping("/podsInfo")
	public ResponseEntity getPodInfoByUuid(@RequestParam(name="uuid") String uuid) {
		// TODO Auto-generated method stub
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    return new ResponseEntity<String>(podService.getPodsInfoByUuid(uuid), httpHeaders, HttpStatus.OK);

	}

}
