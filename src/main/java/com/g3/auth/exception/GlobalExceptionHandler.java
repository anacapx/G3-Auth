package com.g3.auth.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.g3.auth.exception.customException.BadRequestException;
import com.g3.auth.exception.customException.ResourceNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<?> handleResourceNotFoundException (ResourceNotFoundException e, WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(
				e.getStatus().value(), 
				new Date(), 
				e.getMessage(), 
				webRequest.getDescription(false));
		
		return ResponseEntity.status(e.getStatus()).body(errorDetails);
	}
	
	@ExceptionHandler({BadRequestException.class})
	public ResponseEntity<?> handleBadRequestException (BadRequestException e, WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(
				e.getStatus().value(), 
				new Date(), 
				e.getMessage(), 
				webRequest.getDescription(false));
		
		return ResponseEntity.status(e.getStatus()).body(errorDetails);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<List<FormErrorDTO>> handle(MethodArgumentNotValidException exception) {
		List<FormErrorDTO> errorDtoList = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		for (FieldError error : fieldErrors) {
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			errorDtoList.add(new FormErrorDTO(new Date(), HttpStatus.BAD_REQUEST.value(), error.getField(), message));
		}
		return ResponseEntity.badRequest().body(errorDtoList);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails> (errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
