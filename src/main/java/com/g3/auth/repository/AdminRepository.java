package com.g3.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.g3.auth.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	public Optional<Admin> findByEmail(String email);
	public Optional<Admin> findById(Long id);
	
}
