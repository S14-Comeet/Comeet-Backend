package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

/**
 * 추천 관련 예외
 */
public class RecommendationException extends BusinessException {

	public RecommendationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
