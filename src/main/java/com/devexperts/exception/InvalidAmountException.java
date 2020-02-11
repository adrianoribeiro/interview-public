package com.devexperts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid amount.")
public class InvalidAmountException extends RuntimeException {

	private static final long serialVersionUID = -2240525219079107381L;

	@Override
	public String getMessage() {
		return "InvalidAmountException";
	}
}
