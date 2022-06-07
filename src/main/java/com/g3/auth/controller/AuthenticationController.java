package com.g3.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g3.auth.config.security.TokenDTO;
import com.g3.auth.config.security.TokenService;
import com.g3.auth.controller.form.LoginForm;

@RestController
@RequestMapping("login")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<?> authenticate(@RequestBody LoginForm form) {
		UsernamePasswordAuthenticationToken loginData = form.toUsernamePasswordAuthenticationToken();
		try {
			Authentication authentication = authManager.authenticate(loginData);
			String token = tokenService.generateToken(authentication);
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
}
