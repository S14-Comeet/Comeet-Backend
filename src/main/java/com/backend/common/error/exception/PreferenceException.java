package com.backend.common.error.exception;

import com.backend.common.error.ErrorCode;

/**
 * UserPreference 관련 예외
 */
public class PreferenceException extends BusinessException {

	public PreferenceException(ErrorCode errorCode) {
		super(errorCode);
	}
}
