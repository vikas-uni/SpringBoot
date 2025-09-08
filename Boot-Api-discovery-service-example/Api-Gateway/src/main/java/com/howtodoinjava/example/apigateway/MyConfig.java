package com.howtodoinjava.example.apigateway;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {

	@Bean
	@LoadBalanced //Spring provided load balancer works in case there are multiple instances of targer service
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
