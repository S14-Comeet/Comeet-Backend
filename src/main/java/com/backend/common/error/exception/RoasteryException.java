package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class RoasteryException extends BusinessException {
	public RoasteryException(final ErrorCode errorCode) {
		super(errorCode);
	}
}
