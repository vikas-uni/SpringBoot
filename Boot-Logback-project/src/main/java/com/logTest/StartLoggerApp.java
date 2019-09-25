package com.logTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient    // this registers the service to a discovery server with a name 
public class StartLoggerApp {

	public static void main(String[] args) {
		SpringApplication.run(StartLoggerApp.class, args);
	}

}