package com.g3.auth.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g3.auth.controller.dto.AdminDTO;
import com.g3.auth.controller.form.SignUpForm;
import com.g3.auth.model.Admin;
import com.g3.auth.repository.AdminRepository;
import com.g3.auth.repository.ProfileRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ProfileRepository profileRepository;

//	@GetMapping("/{id}")
//	public ResponseEntity<?> findAdmById(@PathVariable Long id) {
//		Optional<Admin> adminOptional = adminRepository.findById(id);
//		if (adminOptional.isPresent()) {
//			return ResponseEntity.ok(adminOptional.get());
//		}
//		return ResponseEntity.notFound().build();
//	}

	@PostMapping
	public ResponseEntity<?> signUp(@RequestBody SignUpForm form) {

		Optional<Admin> adminByEmail = adminRepository.findByEmail(form.getEmail());
		if (adminByEmail.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		Admin newAdmin = adminRepository.save(form.toAdmin(profileRepository));
		AdminDTO adminDTO = new AdminDTO(newAdmin);
		return ResponseEntity.ok(adminDTO);
	}
}
