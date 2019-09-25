package com.howtodoinjava.example.employee.controller;

import java.util.HashMap;
import java.util.Map;


import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.howtodoinjava.example.employee.beans.Employee;

@RestController
public class EmployeeServiceController {
	
	//introducing logger
	 org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeServiceController.class);
	
	
	private static final Map<Integer, Employee> employeeData = new HashMap<Integer, Employee>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3970206781360313502L;

		{
			put(111,new Employee(111,"Employee1"));
			put(222,new Employee(222,"Employee2"));
		}
 
    };
 
    @RequestMapping(value = "/findEmployeeDetails/{employeeId}", method = RequestMethod.GET)
    public Employee getEmployeeDetails(@PathVariable int employeeId) {
        System.out.println("Getting Employee details for " + employeeId);
        logger.info("*****received request******  "+ employeeId);
 
        Employee employee = employeeData.get(employeeId);
        if (employee == null) {
            
        	employee = new Employee(0, "N/A");
            
        }
        
        logger.info("*****returning employee******  "+ employee);
        logger.info("****---------logger-info-findEmployeeDetails-----Here-------*****");
        logger.debug("****---------logger-debug-findEmployeeDetails-----Here-------*****");
        logger.error("****---------logger-error-findEmployeeDetails-----Here-------*****");
        logger.warn("****---------logger-warn-findEmployeeDetails-----Here-------*****");
        return employee;
    }
    
    @RequestMapping(value = "/getTestString/{employeeId}", method = RequestMethod.GET)
    public String getTestString (@PathVariable String employeeId) {
        System.out.println("****---------Here-getTestString-------*****");
        logger.info("****---------logger-info-getTestString-----Here-------*****");
        logger.debug("****---------logger-debug-getTestString-----Here-------*****");
        logger.error("****---------logger-error-getTestString-----Here-------*****");
        logger.warn("****---------logger-warn-getTestString-----Here-------*****");
 
      
        return "received request::::::::"+employeeId;
    }

}
