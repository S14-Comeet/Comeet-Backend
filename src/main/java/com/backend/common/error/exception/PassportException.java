package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class PassportException extends BusinessException {
	public PassportException(final ErrorCode errorCode) {
		super(errorCode);
	}
}
