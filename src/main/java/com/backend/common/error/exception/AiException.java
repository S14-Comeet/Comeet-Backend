package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class AiException extends BusinessException {

	public AiException(ErrorCode errorCode) {
		super(errorCode);
	}
}
