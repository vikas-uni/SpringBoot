package com.jcombat.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Netflix Eureka server is for building service registry server and associated
 * Eureka clients which will register themselves to lookup other services and
 * communicate thru REST API’s.
 * 
 * @author vikasgond
 *
 */

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryMicroserviceServerApplication {

	public static void main(String[] args) {
		// SpringApplication.run(DiscoveryMicroserviceServerApplication.class, args);
		SpringApplication.run(DiscoveryMicroserviceServerApplication.class, args);
	}
}
