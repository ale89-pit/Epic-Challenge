package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.payload.AddressDto;
import com.biblioTech.Security.service.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@PutMapping("/add/{user_id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> addAddressToUser(@PathVariable long user_id, @RequestBody AddressDto address) {
		return ResponseEntity.ok(userService.addAddressUser(user_id, address));
	}

}
