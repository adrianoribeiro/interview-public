package com.devexperts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Account source is not found.")
public class AccountSourceIsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4364074446351552741L;

	@Override
	public String getMessage() {
		return "AccountSourceIsNotFoundException";
	}
}
