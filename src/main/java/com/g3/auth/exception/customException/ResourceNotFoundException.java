package com.g3.auth.exception.customException;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	private HttpStatus status = HttpStatus.NOT_FOUND;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}
}
