package com.g3.auth.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_adm")
public class Admin implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="adm_id")
	private Long id;
	
	@Column(name = "adm_name", nullable = false)
	private String name;
	
	@Column(name = "adm_email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "adm_password", nullable = false)
	private String admPassword;
	
	@ManyToOne
	@JoinColumn(name = "adm_profile")
	@JsonIgnoreProperties("admList")
	private Profile profile;
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(
//			  name = "tb_adm_profiles", 
//			  joinColumns = @JoinColumn(name = "adm_id"), 
//			  inverseJoinColumns = @JoinColumn(name = "profile_id"))
//	private Set<Profile> profiles;

	public Admin() {
	}
	
	public Admin(String name, String email, String password, Profile profile) {
		this.name = name;
		this.email = email;
		this.admPassword = password;
		this.profile = profile;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdmPassword() {
		return admPassword;
	}

	public void setAdmPassword(String password) {
		this.admPassword = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<Profile> profilesList = new ArrayList<Profile>();
		profilesList.add(this.profile);
		return profilesList;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
	
	@Override
	public String getPassword() {
		return this.admPassword;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
