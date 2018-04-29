package com.webcheckers.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}
}
