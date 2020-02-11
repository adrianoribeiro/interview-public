package com.devexperts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Insufficient funds.")
public class InsufficientFundsException extends RuntimeException {

	private static final long serialVersionUID = 798855655942570163L;
	
	@Override
	public String getMessage() {
		return "InsufficientFundsException";
	}
}
