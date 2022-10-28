package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PodResultDTO {

	/**
	 * service id
	 */
	private String uuid;
	
	/**
	 * 
	 * podName
	 */
	private String hostName;
	
	/**
	 *  reload result
	 */
	private String result = "";
	
	/**
	 * adapter receive request time
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class) 
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime time;
	
	public PodResultDTO(String uuid,String hostName) {
		this.uuid = uuid;
		this.hostName = hostName;
	}
	
	public PodResultDTO(String uuid,String hostName,String result) {
		this(uuid,hostName);
		this.result =result;
	}
	
	public void setResult(String result) {
		this.result = result == null? "":result;
	}
}
