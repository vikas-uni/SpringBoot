package com.logTest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/myRest")
public class MyRestController {

	@RequestMapping(value = "/mappingTest/{employeeId}", method = RequestMethod.GET)
	@ResponseBody
	public String getMappingTest(@PathVariable String employeeId) {
		System.out.println("****---------Here-getMappingTest-------*****");

		return "received request::::::::" + employeeId;
	}

	@RequestMapping(value = "/mappingTest/{deptId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String postMappingTest(@RequestBody Employee employeeId, @PathVariable int deptId) {
		System.out.println("****---------Here-postMappingTest-------*****");

		return "received request::::::::" + employeeId.getName() + "  --dept: " + deptId;
	}
}
