package com.biblioTech.Security.service;

import com.biblioTech.Security.payload.LoginDto;
import com.biblioTech.Security.payload.RegisterDto;

public interface AuthService {
    
	String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    
}
