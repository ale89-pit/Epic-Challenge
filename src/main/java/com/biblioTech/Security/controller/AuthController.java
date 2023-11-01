package com.biblioTech.Security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioTech.Security.payload.JWTAuthResponse;
import com.biblioTech.Security.payload.LoginDto;
import com.biblioTech.Security.payload.RegisterDto;
import com.biblioTech.Security.service.AuthService;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	// Build Login REST API
	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {

		String token = authService.login(loginDto);

		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setUsername(loginDto.getUsername());
		jwtAuthResponse.setAccessToken(token);

		return ResponseEntity.ok(jwtAuthResponse);
	}

	// Build Register REST API
	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping(value = { "/registerLibrary", "/signup" })
	public ResponseEntity<String> registerLibrary(@RequestBody RegisterDto registerDto) {
		String response = authService.registerLibrary(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
		return authService.confirmEmail(confirmationToken);
	}

	@RequestMapping(value = "/confirm-library-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> confirmLibraryAccount(@RequestParam("token") String confirmationToken) {
		return authService.confirmEmailByLibrary(confirmationToken);
	}
}
