package com.g3.auth.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.g3.auth.model.Admin;
import com.g3.auth.model.Profile;
import com.g3.auth.repository.ProfileRepository;

public class SignUpForm {

	@NotNull @NotEmpty
	private String name;
	
	@NotNull @NotEmpty
	private String email;
	
	@NotNull @NotEmpty
	private String password;
	
	public SignUpForm(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = new BCryptPasswordEncoder().encode(password);
	}
	
	public Admin toAdmin(ProfileRepository profileRepository) {
		Profile profile = profileRepository.findById(1).get();
		
		Admin admin = new Admin(this.name, this.email, this.password, profile);
		return admin;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
}
