package com.g3.auth.exception.customException;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	private HttpStatus status = HttpStatus.BAD_REQUEST;
	
	public BadRequestException(String message) {
		super(message);
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}
}
