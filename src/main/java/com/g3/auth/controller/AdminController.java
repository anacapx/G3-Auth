package com.g3.auth.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g3.auth.config.security.TokenDTO;
import com.g3.auth.config.security.TokenService;
import com.g3.auth.controller.dto.AdminDTO;
import com.g3.auth.controller.form.LoginForm;
import com.g3.auth.controller.form.SignUpForm;
import com.g3.auth.model.Admin;
import com.g3.auth.repository.AdminRepository;
import com.g3.auth.repository.ProfileRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class AdminController {

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

	@GetMapping("admin/{id}")
	public ResponseEntity<AdminDTO> findAdmById(@PathVariable Long id) {
		Optional<Admin> adminOptional = adminRepository.findById(id);
		if (adminOptional.isPresent()) {
			AdminDTO adminDTO = new AdminDTO(adminOptional.get());
			return ResponseEntity.ok(adminDTO);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("admin")
	public ResponseEntity<AdminDTO> signUp(@RequestBody @Valid SignUpForm form) {

		Optional<Admin> adminByEmail = adminRepository.findByEmail(form.getEmail());
		if (adminByEmail.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		Admin newAdmin = adminRepository.save(form.toAdmin(profileRepository));
		AdminDTO adminDTO = new AdminDTO(newAdmin);
		return ResponseEntity.ok(adminDTO);
	}
	
	@PostMapping("login")
	public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken loginData = form.toUsernamePasswordAuthenticationToken();
		try {
			Authentication authentication = authManager.authenticate(loginData);
			String token = tokenService.generateToken(authentication);
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}	
	}
	
	@GetMapping("validate")
	public ResponseEntity<Authentication> validateToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().build();
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
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(authentication);

	}
	
}
