package com.backend.common.error;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode {

	/**
	 * Common Error
	 */
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "C-001", "잘못된 요청입니다."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "C-002", "리소스를 찾을 수 없습니다."),
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "C-003", "유효하지 않은 입력값입니다."),
	UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "C-005", "승인되지 않은 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "서버 오류가 발생했습니다."),

	/**
	 * Database Error
	 */
	DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C-001", "데이터베이스 오류입니다."),
	DUPLICATED_KEY(HttpStatus.CONFLICT,"D-002" , "중복된 키입니다."),

	/**
	 * User Error
	 */
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "유저를 찾을 수 없습니다."),
	NICKNAME_NOT_BLANK(HttpStatus.BAD_REQUEST, "U-002", "닉네임은 필수 입력값입니다."),
	NICKNAME_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "U-003", "닉네임 형식이 올바르지 않습니다."),

	/**
	 * Auth/JWT Error
	 */
	MALFORMED_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "A-001", "유효하지 않은 토큰 형식입니다."),
	INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-002", "잘못된 토큰 타입입니다."),
	TOKEN_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "A-003", "만료된 토큰입니다."),
	TOKEN_BLACKLISTED_EXCEPTION(HttpStatus.UNAUTHORIZED, "A-004", "블랙리스트에 등록된 토큰입니다."),
	TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "A-005", "요청으로부터 토큰을 찾지 못했습니다."),
	REFRESH_TOKEN_NOT_MATCH(HttpStatus.BAD_REQUEST, "A-006", "리프레시 토큰이 일치하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
