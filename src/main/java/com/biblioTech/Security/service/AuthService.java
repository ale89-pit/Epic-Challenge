package com.biblioTech.Security.service;

import org.springframework.http.ResponseEntity;

import com.biblioTech.Security.entity.Library;
import com.biblioTech.Security.payload.LoginDto;
import com.biblioTech.Security.payload.RegisterDto;

public interface AuthService {

	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);

	String registerLibrary(RegisterDto registerDto);
	
	Library registerLibraryByRunner(RegisterDto registerDto);

	ResponseEntity<?> confirmEmail(String confirmationToken);

	ResponseEntity<?> confirmEmailByLibrary(String confirmationToken);

}
