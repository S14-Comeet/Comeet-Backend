package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class S3FileException extends BusinessException{
	public S3FileException(final ErrorCode errorCode) {
		super(errorCode);
	}
}
