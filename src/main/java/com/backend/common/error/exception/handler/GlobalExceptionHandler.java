package com.backend.common.error.exception.handler;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.AuthException;
import com.backend.common.error.exception.BusinessException;
import com.backend.common.error.exception.UserException;
import com.backend.common.response.BaseResponse;
import com.backend.common.response.ErrorResponse;
import com.backend.common.util.LoggingUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleException(Exception ex, HttpServletRequest request) {
		LoggingUtil.logException("지정되지 않은 예외 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, request);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleSQLException(
		SQLException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("SQLException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ErrorCode.DATABASE_ERROR, request);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleBusinessException(
		BusinessException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("BusinessException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logValidationException(ex, request);
		ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT, request);
		response.addValidationErrors(ex.getBindingResult());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleDataAccessException(
		DataAccessException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("DataAccessException 예외 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ErrorCode.DATABASE_ERROR, request);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleDuplicateKeyException(
		DataAccessException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("DuplicateKeyException 예외 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ErrorCode.DUPLICATED_KEY, request);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleUserException(
		UserException ex,
		HttpServletRequest request
	) {

		LoggingUtil.logException("UserException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(AuthException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleAuthException(
		AuthException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("AuthException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}
}