package com.howtodoinjava.example.employee.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.howtodoinjava.example.employee.GitHubLookupService;
import com.howtodoinjava.example.employee.beans.Employee;

@RestController
public class EmployeeServiceController {

	// introducing logger
	Logger logger = LoggerFactory.getLogger(EmployeeServiceController.class);

	@Autowired
	private GitHubLookupService lookupService;

	@Autowired
	private Environment environment;

	private static final Map<Integer, Employee> employeeData = new HashMap<Integer, Employee>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3970206781360313502L;

		{
			put(111, new Employee(111, "Employee1"));
			put(222, new Employee(222, "Employee2"));
		}

	};

	@RequestMapping(value = "/findEmployeeDetails/{employeeId}", method = RequestMethod.GET)
	public Employee getEmployeeDetails(@PathVariable int employeeId) {
		System.out.println("Getting Employee details for " + employeeId);
		logger.info("*****received request******  " + employeeId);

		Employee employee = employeeData.get(employeeId);
		if (employee == null) {

			employee = new Employee(0, "N/A");

		}

		logger.info("*****returning employee******  " + employee);
		logger.info("****---------logger-info-findEmployeeDetails-----Here-------*****");
		logger.debug("****---------logger-debug-findEmployeeDetails-----Here-------*****");
		logger.error("****---------logger-error-findEmployeeDetails-----Here-------*****");
		logger.warn("****---------logger-warn-findEmployeeDetails-----Here-------*****");
		return employee;
	}

	@RequestMapping(value = "/getTestString/{employeeId}", method = RequestMethod.GET)
	public String getTestString(@PathVariable String employeeId) {
		System.out.println("****---------Here-getTestString-------*****");
		logger.info("****---------logger-info-getTestString-----Here-------*****");
		logger.debug("****---------logger-debug-getTestString-----Here-------*****");
		logger.error("****---------logger-error-getTestString-----Here-------*****");
		logger.warn("****---------logger-warn-getTestString-----Here-------*****");

		String port = environment.getProperty("local.server.port");
		return "received request::::::::" + employeeId + " on port:" + port;
	}

	@RequestMapping(value = "/testAsync/{employeeId}", method = RequestMethod.GET)
	public CompletableFuture<Employee> testAsync(@PathVariable String employeeId) throws InterruptedException {
		long start = System.currentTimeMillis();
		logger.info("****---------logger-info-async method start*****");
		CompletableFuture<Employee> findUser = lookupService.findUser(employeeId);
		CompletableFuture<Employee> findUser2 = lookupService.findUser2(employeeId);

		Function<? super Employee, ? extends CompletionStage<Employee>> fn = emp -> {
			logger.info("in fn combined");
			logger.info("emp from findUser: " + emp.getId());
			Employee emp2;
			try {
				emp2 = findUser2.get();
				CompletableFuture<Employee> future = CompletableFuture
						.completedFuture(new Employee(emp.getId() + emp2.getId(), emp2.getName() + emp.getName()));
				return future;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		};

		CompletableFuture<Employee> combined = findUser.thenCompose(fn);

		// Wait until they are all done
		// CompletableFuture.allOf(findUser, findUser).join();

		/*
		 * try { logger.info("****---------after join completes*****");
		 * System.out.println(findUser.get().getId());
		 * System.out.println(findUser2.get().getId()); } catch (InterruptedException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (ExecutionException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		logger.info("Servlet thread released");
		return combined;
	}

}
