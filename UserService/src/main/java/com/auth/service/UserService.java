package com.auth.service;


import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth.entity.User;
import com.auth.repo.UserRepo;
import com.auth.responsesDTO.LoginRequest;
import com.auth.responsesDTO.LoginResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final AuthenticationManager authmanager;
	private final UserRepo userRepo;
	private final JwtService jwtService;

	public LoginResponse verify(LoginRequest req) {
		
		Authentication auth=authmanager.authenticate( new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));
		if(auth.isAuthenticated())
		{
			
			
			User user=userRepo.findByUsername(req.getUsername());
			
			
			String token=jwtService.generateToken(req.getUsername());
			LoginResponse loginResponse=new LoginResponse(token,user.getRole(),LocalDate.now(),LocalTime.now());
			return loginResponse;
			
			
		}
	
		
		return null;
	}

}
