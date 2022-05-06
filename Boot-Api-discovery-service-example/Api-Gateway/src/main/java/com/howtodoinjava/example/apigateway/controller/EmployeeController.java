package com.howtodoinjava.example.apigateway.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class EmployeeController {

	Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/employeeDetails/{employeeid}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "fallbackMethod")
	public String getStudents(@PathVariable int employeeid) {
		LOGGER.info("Getting Employee details for " + employeeid);

		String response = restTemplate.exchange("http://employee-service/findEmployeeDetails/{employeeid}",
				HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
				}, employeeid).getBody();

		LOGGER.info("Response Body " + response);

		return "Employee Id -  " + employeeid + " [ Employee Details " + response + " ]";
	}

	@RequestMapping(value = "/testMethod/{testStr}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "myFallbackMethod")
	public String testMethod(@PathVariable String testStr) {
		LOGGER.info("Getting Employee details for " + testStr);

		String response = restTemplate.exchange("http://employee-service/getTestString/{employeeId}",
				HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
				}, testStr).getBody();

		LOGGER.info("Response Body " + response);

		return "Employee Id -  " + testStr + " [ Employee Details " + response + " ]";
	}
	
	@RequestMapping(value = "/testAsync/{employeeId}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "asyncFallbackMethod")
    public ResponseEntity<Object> testAsync (@PathVariable String employeeId) throws InterruptedException {
		LOGGER.info("in testAsync");
		String response = restTemplate.exchange("http://employee-service/testAsync/{employeeId}",
				HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
				}, employeeId).getBody();

		LOGGER.info("got result testAsync Response Body " + response);

		return ResponseEntity.ok().body(response);
    }

	public String fallbackMethod(int employeeid) {

		return "Fallback response:: No employee details available temporarily";
	}

	public String myFallbackMethod(String myStr) {

		return "Fallback response:: No details available temporarily " + myStr;
	}
	
	public ResponseEntity<Object> asyncFallbackMethod(String myStr) {

		return ResponseEntity.ok("Fallback response asyncFallbackMethod:: No details available temporarily " + myStr);
	}

	/*@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}*/
}
