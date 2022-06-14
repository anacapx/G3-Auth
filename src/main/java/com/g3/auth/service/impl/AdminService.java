package com.g3.auth.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.g3.auth.config.security.TokenDTO;
import com.g3.auth.config.security.TokenService;
import com.g3.auth.controller.dto.AdminDTO;
import com.g3.auth.controller.form.LoginForm;
import com.g3.auth.controller.form.SignUpForm;
import com.g3.auth.exception.customException.BadRequestException;
import com.g3.auth.exception.customException.ResourceNotFoundException;
import com.g3.auth.model.Admin;
import com.g3.auth.repository.AdminRepository;
import com.g3.auth.repository.ProfileRepository;
import com.g3.auth.service.interfaces.IAdminService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
@Component
public class AdminService implements IAdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	TokenService tokenService;
	
	@Value("${auth.jwt.secret}")
	private String secret;
	
	@Override
	public AdminDTO findAdmById(Long id) {
		Optional<Admin> adminOptional = adminRepository.findById(id);
		if (adminOptional.isPresent()) {
			AdminDTO adminDTO = new AdminDTO(adminOptional.get());
			return adminDTO;
		}
		throw new ResourceNotFoundException("Admin not found.");
	}

	@Override
	public AdminDTO signUp(SignUpForm form) {
		Optional<Admin> adminByEmail = adminRepository.findByEmail(form.getEmail());
		if (adminByEmail.isPresent()) {
			throw new BadRequestException("Email already in use.");
		}
		Admin newAdmin = adminRepository.save(form.toAdmin(profileRepository));
		AdminDTO adminDTO = new AdminDTO(newAdmin);
		return adminDTO;
	}

	@Override
	public TokenDTO authenticate(LoginForm form) {
		UsernamePasswordAuthenticationToken loginData = form.toUsernamePasswordAuthenticationToken();
		try {
			Authentication authentication = authManager.authenticate(loginData);
			String token = tokenService.generateToken(authentication);
			return new TokenDTO(token, "Bearer");
		} catch (AuthenticationException e) {
			throw new BadRequestException("Invalid token.");
		}
	}

	@Override
	public Authentication validateToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			throw new BadRequestException("Invalid token.");
		}
		token = token.substring(7, token.length());

//		Gets user Id
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		Long userId = Long.parseLong(claims.getSubject());

//		Gets user
		Admin user = adminRepository.findById(userId).get();

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities()));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new BadRequestException("Invalid token.");
		}
		return authentication;

	}

}
