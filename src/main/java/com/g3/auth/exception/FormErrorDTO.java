package com.g3.auth.exception;

import java.util.Date;

public class FormErrorDTO {

	private Date currentDate;
	private Integer status;
	private String field;
	private String errorMessage;
	
	public FormErrorDTO(Date currentDate, Integer status, String field, String errorMessage) {
		this.currentDate = currentDate;
		this.status = status;
		this.field = field;
		this.errorMessage = errorMessage;
	}
	
	public Date getCurrentDate() {
		return currentDate;
	}

	public Integer getStatus() {
		return status;
	}

	public String getField() {
		return field;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
