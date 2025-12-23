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
import com.backend.common.error.exception.AiException;
import com.backend.common.error.exception.AuthException;
import com.backend.common.error.exception.BeanException;
import com.backend.common.error.exception.BusinessException;
import com.backend.common.error.exception.FileException;
import com.backend.common.error.exception.FlavorException;
import com.backend.common.error.exception.MenuException;
import com.backend.common.error.exception.PassportException;
import com.backend.common.error.exception.ReviewException;
import com.backend.common.error.exception.RoasteryException;
import com.backend.common.error.exception.S3FileException;
import com.backend.common.error.exception.StoreException;
import com.backend.common.error.exception.UserException;
import com.backend.common.error.exception.VisitException;
import com.backend.common.response.BaseResponse;
import com.backend.common.response.ErrorResponse;
import com.backend.common.util.LoggingUtil;

import io.jsonwebtoken.JwtException;
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
	public ResponseEntity<BaseResponse<ErrorResponse>> handleSQLException(SQLException ex, HttpServletRequest request) {
		LoggingUtil.logException("SQLException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ErrorCode.DATABASE_ERROR, request);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleJwtException(JwtException ex, HttpServletRequest request) {
		LoggingUtil.logException("JwtException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TOKEN, request);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponse.fail(response));
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
		DuplicateKeyException ex,
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

	@ExceptionHandler(VisitException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleVisitException(
		VisitException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("VisitException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(ReviewException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleReviewException(
		ReviewException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("ReviewException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	// ====== 아래부터 누락된 Exception 추가 ======

	@ExceptionHandler(AiException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleAiException(AiException ex, HttpServletRequest request) {
		LoggingUtil.logException("AiException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(BeanException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleBeanException(
		BeanException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("BeanException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleFileException(
		FileException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("FileException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(S3FileException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleS3FileException(
		S3FileException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("S3FileException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(FlavorException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleFlavorException(
		FlavorException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("FlavorException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(MenuException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleMenuException(
		MenuException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("MenuException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(PassportException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handlePassportException(
		PassportException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("PassportException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(RoasteryException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleRoasteryException(
		RoasteryException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("RoasteryException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}

	@ExceptionHandler(StoreException.class)
	public ResponseEntity<BaseResponse<ErrorResponse>> handleStoreException(
		StoreException ex,
		HttpServletRequest request
	) {
		LoggingUtil.logException("StoreException 발생", ex, request);
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), request);
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(BaseResponse.fail(response));
	}
}
