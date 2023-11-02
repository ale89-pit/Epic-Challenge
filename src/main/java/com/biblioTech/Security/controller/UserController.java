package com.biblioTech.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.payload.UserDto;
import com.biblioTech.Security.service.LibraryService;
import com.biblioTech.Security.service.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	LibraryService libraryService;

	// metodo per aggiornare anche solo unn campo dell'utente Dto
	@PutMapping("/{user_id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> updateUser(@PathVariable long user_id, @RequestBody UserDto userToUpdate) {
		return ResponseEntity.ok(userService.updateUser(user_id, userToUpdate));
	}
	
	@GetMapping("/{username}/{email}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
	public ResponseEntity<?> findByUsernameOrEmail(@PathVariable String username,@PathVariable String email){
		return ResponseEntity.ok(userService.findByUsernameOrEmail(username, email)) ;
		
	}


}
