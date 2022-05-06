package com.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerApis {

	@GetMapping("/publicApi")
	public String getPublic() {
		return "public api response";
	}
	
	@GetMapping("/privateApi")
	public String getPrivate() {
		return "private api response";
	}
}
