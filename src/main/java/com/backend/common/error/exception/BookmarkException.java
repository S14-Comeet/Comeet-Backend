package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

public class BookmarkException extends BusinessException {
	public BookmarkException(ErrorCode errorCode) {
		super(errorCode);
	}
}
