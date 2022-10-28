package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.PodResultDTO;
import com.example.demo.service.PodResultService;

@Controller
@RequestMapping("/")
@ResponseBody
public class ResultController {

	@Autowired
	private PodResultService podService;

	@PostMapping(value="/adapter/savePodInfo",consumes = "application/json")
	public String savePodInfo(@RequestBody PodResultDTO podInfoDto) {
//		PodResultDTO podInfoDto = new PodResultDTO(uuid, hostName);
		podService.savePodInfo(podInfoDto);
		return "podInfo saved";
	}

	@PostMapping("/adapter/savePodInfoWithResult")
	public String savePodInfoWithResult(@RequestBody PodResultDTO podInfoDto) {
		podService.savePodInfo(podInfoDto);
		return "podInfo saved";
	}

	@GetMapping("/adapter/getPodsInfo")
	public ResponseEntity getPodInfoByUuid(@RequestParam(name= "uuid") String uuid) {
		// TODO Auto-generated method stub
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    return new ResponseEntity<String>(podService.getPodsInfoByUuid(uuid), httpHeaders, HttpStatus.OK);

	}

}
