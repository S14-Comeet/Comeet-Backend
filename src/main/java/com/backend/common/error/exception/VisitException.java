package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class VisitException extends BusinessException {
	public VisitException(final ErrorCode errorCode) {
		super(errorCode);
	}
}