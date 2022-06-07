package com.g3.auth.controller.dto;

import com.g3.auth.model.Admin;

public class AdminDTO {

	private String name;
	private String email;
	private String profileName;
	
	public AdminDTO(Admin admin) {
		this.name = admin.getName();
		this.email = admin.getEmail();
		this.profileName = "ADMIN";
	}
		
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getProfileName() {
		return profileName;
	}
	
}
