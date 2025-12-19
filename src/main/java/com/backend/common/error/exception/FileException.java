package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class FileException extends BusinessException{
	public FileException(final ErrorCode errorCode) {
		super(errorCode);
	}
}
