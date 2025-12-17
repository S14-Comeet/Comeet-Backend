package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class MenuException extends BusinessException {
	public MenuException(final ErrorCode errorCode) {
		super(errorCode);
	}
}
