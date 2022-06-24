package com.g3.auth.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

	@NotNull @NotEmpty
	private String email;
	
	@NotNull @NotEmpty
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
