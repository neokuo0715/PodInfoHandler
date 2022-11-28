package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import reactor.core.publisher.Mono;

@Service
public class ProducerService {

	@Autowired
	private JmsTemplate jmsTemplate;

	private WebClient webClient;

	@Value("${mordermtear.url}")
	private String mordermtearUrl;

	private String url = "demo/test";

	public ProducerService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl(mordermtearUrl).build();
	}

	public void sendToQueue(String uuid) {
		// call another server
		Mono<ResponseEntity<Void>> response = webClient.get().uri(mordermtearUrl + url).retrieve().toBodilessEntity();
		System.out.println(response.block().getStatusCodeValue());
		if (response.block().getStatusCodeValue() == 200) {
			jmsTemplate.convertAndSend("wut", uuid);
		}
	}

}
