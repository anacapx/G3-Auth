package com.g3.auth.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

	private String email;
	private String password;
	
	public LoginForm(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken () {
		return new UsernamePasswordAuthenticationToken(this.email, this.password);
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
}
