package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class UserException extends BusinessException {

	public UserException(ErrorCode errorCode) {
		super(errorCode);
	}
}
