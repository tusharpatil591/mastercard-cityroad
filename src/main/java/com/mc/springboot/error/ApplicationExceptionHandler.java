package com.mc.springboot.error;

import org.springframework.http.HttpStatus;

/**
 * This class is used to handle custom exception within application.
 * @author tuashar.patil
 *
 */
public class ApplicationExceptionHandler extends RuntimeException{

	HttpStatus errorStatus;


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ApplicationExceptionHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ApplicationExceptionHandler(String message) {
		super(message);
	}

	public ApplicationExceptionHandler(HttpStatus error_status, String message) {
		super(message);
		errorStatus = error_status;
	}

	public HttpStatus getErrorStatus() {
		return errorStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param cause
	 */
	public ApplicationExceptionHandler(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ApplicationExceptionHandler(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ApplicationExceptionHandler(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
