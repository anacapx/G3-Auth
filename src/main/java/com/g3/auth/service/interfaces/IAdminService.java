package com.g3.auth.service.interfaces;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.g3.auth.config.security.TokenDTO;
import com.g3.auth.controller.dto.AdminDTO;
import com.g3.auth.controller.form.LoginForm;
import com.g3.auth.controller.form.SignUpForm;

public interface IAdminService {
	public AdminDTO findAdmById(Long id);
	public AdminDTO signUp(SignUpForm form);
	public TokenDTO authenticate(LoginForm form);
	public Authentication validateToken(HttpServletRequest request);
}
