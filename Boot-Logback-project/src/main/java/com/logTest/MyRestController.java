package com.logTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//we can map different url's to same resource in request mapping
@RestController
@RequestMapping(value = {"/myRest","/testUrl"})
public class MyRestController {
	private static final Logger logger = LoggerFactory.getLogger(MyRestController.class);

	//we can map different methods to same uri as below
	@RequestMapping(value = "/mappingTest/{employeeId}", method = {RequestMethod.GET,RequestMethod.PUT})
	@ResponseBody
	public String getMappingTest(@PathVariable String employeeId) {
		System.out.println("****---------Here-getMappingTest-------*****");

		logger.info("logged::" + "****---------Here-getMappingTest-------*****");
		return "received request::::::::" + employeeId;
	}

	@RequestMapping(value = "/mappingTest/{deptId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String postMappingTest(@RequestBody Employee employeeId, @PathVariable int deptId) {
		System.out.println("****---------Here-postMappingTest-------*****");
		logger.info("logged::" + "****---------Here-postMappingTest-------*****");
		return "received request::::::::" + employeeId.getName() + "  --dept: " + deptId;
	}
}
