package com.g3.auth.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_profile")
public class Profile implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id")
	private Integer id = 1;
	
	@Column(name = "profile_name")
	private String name = "ADMIN";
	
	@OneToMany(mappedBy = "profile")
	@JsonIgnoreProperties("profile")
	private List<Admin> admList;
	
//	@ManyToMany(mappedBy = "profiles", fetch = FetchType.EAGER)
//	private Set<Admin> admins;
	
	public Profile() {
	}
	
	@Override
	public String getAuthority() {
		return this.name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
