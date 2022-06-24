package com.g3.auth.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.g3.auth.model.Admin;
import com.g3.auth.repository.AdminRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	AdminRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Admin> admin = repository.findByEmail(username);
		if (admin.isPresent()) {
			return admin.get();
		}
		throw new UsernameNotFoundException("Invalid username.");
	}

}
