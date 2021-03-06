package com.howtodoinjava.example.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class EmployeeController {

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/employeeDetails/{employeeid}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "fallbackMethod")
	public String getStudents(@PathVariable int employeeid) {
		System.out.println("Getting Employee details for " + employeeid);

		String response = restTemplate.exchange("http://employee-service/findEmployeeDetails/{employeeid}",
				HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
				}, employeeid).getBody();

		System.out.println("Response Body " + response);

		return "Employee Id -  " + employeeid + " [ Employee Details " + response + " ]";
	}

	@RequestMapping(value = "/testMethod/{testStr}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "myFallbackMethod")
	public String testMethod(@PathVariable String testStr) {
		System.out.println("Getting Employee details for " + testStr);

		String response = restTemplate.exchange("http://employee-service/getTestString/{employeeId}",
				HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
				}, testStr).getBody();

		System.out.println("Response Body " + response);

		return "Employee Id -  " + testStr + " [ Employee Details " + response + " ]";
	}

	public String fallbackMethod(int employeeid) {

		return "Fallback response:: No employee details available temporarily";
	}

	public String myFallbackMethod(String myStr) {

		return "Fallback response:: No details available temporarily " + myStr;
	}

	/*@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}*/
}
