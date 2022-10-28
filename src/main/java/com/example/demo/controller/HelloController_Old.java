package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PodDTO_Old;
import com.example.demo.service.HelloService_Old;

@RestController
public class HelloController_Old {

	@Autowired
	private HelloService_Old helloService;

	
	
	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	@RequestMapping(path = "/{uuid}/{podname}",method = RequestMethod.POST)
	public String savePodInfo(@PathVariable("uuid") String uuid, @PathVariable("podname") String podname
			) {
		helloService.savePodInfoJSONformat(new PodDTO_Old(uuid,podname,null, null, null));
		return "OK";
	}
	
	
	@RequestMapping(path = "/{uuid}/{podname}/{status}",method = RequestMethod.POST)
	public String savePodInfo(@PathVariable("uuid") String uuid, @PathVariable("podname") String podname,
			@PathVariable("status") String status) {
		
		helloService.savePodInfoJSONformat(new PodDTO_Old(uuid,podname,status, null, null));
		return "OK";
	}

	@RequestMapping("/{uuid}")
	public String getPodInfoByuuid(@PathVariable("uuid") String uuid) {
		return helloService.getPodInfoWithJSONFormat(uuid);
	}
}