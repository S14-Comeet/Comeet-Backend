package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class AuthException extends BusinessException{
	public AuthException(final ErrorCode errorCode) {
		super(errorCode);
	}
}
