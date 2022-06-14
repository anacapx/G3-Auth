package com.g3.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g3.auth.config.security.TokenDTO;
import com.g3.auth.controller.dto.AdminDTO;
import com.g3.auth.controller.form.LoginForm;
import com.g3.auth.controller.form.SignUpForm;
import com.g3.auth.service.impl.AdminService;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class AdminController {

	@Autowired
	private AdminService admService;

	@GetMapping("admin/{id}")
	public ResponseEntity<AdminDTO> findAdmById(@PathVariable Long id) {
		AdminDTO adminDTO = admService.findAdmById(id);
		return ResponseEntity.ok(adminDTO);
	}

	@PostMapping("admin")
	public ResponseEntity<AdminDTO> signUp(@RequestBody @Valid SignUpForm form) {
		AdminDTO adminDTO = admService.signUp(form);
		return ResponseEntity.ok(adminDTO);
	}

	@PostMapping("login")
	public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginForm form) {
		TokenDTO tokenDTO = admService.authenticate(form);
		return ResponseEntity.ok(tokenDTO);
	}

	@GetMapping("validate")
	public ResponseEntity<Authentication> validateToken(HttpServletRequest request) {
		Authentication authentication = admService.validateToken(request);
		return ResponseEntity.ok(authentication);

	}

}
