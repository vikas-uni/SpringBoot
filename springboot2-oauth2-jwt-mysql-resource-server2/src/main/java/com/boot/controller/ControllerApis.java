package com.boot.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerApis {

	@GetMapping("/publicApi")
	public String getPublic() {
		return "public api response";
	}
	
	@GetMapping("/privateApi")
	//@PreAuthorize("hasAuthority('role_user')")
	public String getPrivate() {
		return "private api response";
	}
	
	@GetMapping("/testApi")
	//@PreAuthorize("hasAuthority('role_admin')")
	public String testApi() {
		return "testApi api response";
	}
	
	@GetMapping("/secured")
	@PreAuthorize("hasAuthority('role_admin')")
	public String secured() {
		return "secured api response";
	}
}
