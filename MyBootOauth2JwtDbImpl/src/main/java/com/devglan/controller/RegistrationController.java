package com.devglan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devglan.model.User;
import com.devglan.service.UserService;

@RestController
@RequestMapping("/register")
public class RegistrationController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody User user) {
		User save = userService.save(user);
		ResponseEntity<String> resp = new ResponseEntity<String>(save.getUsername() + " Saved successfully",
				HttpStatus.OK);
		return resp;
	}
}
