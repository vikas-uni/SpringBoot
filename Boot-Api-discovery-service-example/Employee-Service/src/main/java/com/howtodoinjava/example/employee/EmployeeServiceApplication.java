package com.howtodoinjava.example.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * In this demo, we will create three applications.
 * 
 * Employee Service – This microservice application is responsible to fetch data
 * of Employees. 
 * Api-Gateway – This application is to provide common gateway
 * while accessing different microservices. In the following example it will act
 * as a gateway to Employee Service above. 
 * Eureka Server – This microservice
 * application will provide service discovery and registration of above
 * microservices
 * 
 * @author vikasgond
 *
 */
//https://howtodoinjava.com/spring-cloud/microservices-monitoring/

@SpringBootApplication
@EnableEurekaClient // this registers the service to a discovery server with a name
@EnableAsync
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}
}
