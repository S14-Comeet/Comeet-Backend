package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class FlavorException extends BusinessException {

	public FlavorException(ErrorCode errorCode) {
		super(errorCode);
	}
}
