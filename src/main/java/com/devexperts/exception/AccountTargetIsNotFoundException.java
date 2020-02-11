package com.devexperts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Account target is not found.")
public class AccountTargetIsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3207811763293405755L;

	@Override
	public String getMessage() {
		return "AccountTargetIsNotFoundException";
	}
}
