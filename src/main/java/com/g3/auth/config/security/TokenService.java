package com.g3.auth.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.g3.auth.controller.dto.AdminDTO;
import com.g3.auth.model.Admin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${auth.jwt.expiration}")
	private String expiration;
	
	@Value("${auth.jwt.secret}")
	private String secret;
	
	public String generateToken(Authentication authentication) {
		Admin loggedAdmin = (Admin) authentication.getPrincipal();
		AdminDTO loggedAdminDTO = new AdminDTO(loggedAdmin);
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("G3 - Auth API")
				.setSubject(loggedAdminDTO.getId().toString())
				.setIssuedAt(today)
				.signWith(SignatureAlgorithm.HS256, secret)
				.setExpiration(expirationDate)
				.compact();
	}
	
	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Long getUserId (String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
	
}
