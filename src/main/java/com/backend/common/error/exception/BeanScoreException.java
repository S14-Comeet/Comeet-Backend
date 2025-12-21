package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

/**
 * BeanScore 관련 예외
 */
public class BeanScoreException extends BusinessException {

	public BeanScoreException(ErrorCode errorCode) {
		super(errorCode);
	}
}
