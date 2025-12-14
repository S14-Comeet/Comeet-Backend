package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class StoreException extends BusinessException {
	public StoreException(ErrorCode errorCode) {
		super(errorCode);
	}
}
