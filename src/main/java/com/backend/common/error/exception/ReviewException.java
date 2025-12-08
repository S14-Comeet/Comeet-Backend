package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class ReviewException extends BusinessException {
	public ReviewException(final ErrorCode errorCode) {
		super(errorCode);
	}
}
