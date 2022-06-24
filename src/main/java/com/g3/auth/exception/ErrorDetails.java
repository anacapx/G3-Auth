package com.g3.auth.exception;

import java.util.Date;

public class ErrorDetails {

	private Integer status;
	private Date currentDate;
	private String message;
	private String details;

	public ErrorDetails(Integer status, Date currentDate, String message, String details) {
		super();
		this.status = status;
		this.currentDate = currentDate;
		this.message = message;
		this.details = details;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}
