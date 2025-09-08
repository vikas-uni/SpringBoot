package com.bbtutorials.echo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

	private static Logger LOGGER = LoggerFactory.getLogger(EchoController.class);

	@GetMapping("/echo/{name}")
	public String echo(@PathVariable String name) {
		LOGGER.info("-----Request received----" + name);
		return "Hello " + name;
	}

}
