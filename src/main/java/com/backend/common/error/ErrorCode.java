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
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C-004", "서버 오류가 발생했습니다."),
	UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "C-005", "승인되지 않은 요청입니다."),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "C-006", "접근이 거부되었습니다."),

	/**
	 * Database Error
	 */
	DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "D-001", "데이터베이스 오류입니다."),
	DUPLICATED_KEY(HttpStatus.CONFLICT, "D-002", "중복된 키입니다."),
	/**
	 * User Error
	 */
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "유저를 찾을 수 없습니다."),
	NICKNAME_NOT_BLANK(HttpStatus.BAD_REQUEST, "U-002", "닉네임은 필수 입력값입니다."),
	NICKNAME_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "U-003", "닉네임 형식이 올바르지 않습니다."),
	INVALID_USER(HttpStatus.BAD_REQUEST, "U-004", "유효하지 않은 사용자입니다."),
	/**
	 * Auth/JWT Error
	 */
	MALFORMED_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "A-001", "유효하지 않은 토큰 형식입니다."),
	INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-002", "잘못된 토큰 타입입니다."),
	TOKEN_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "A-003", "만료된 토큰입니다."),
	TOKEN_BLACKLISTED_EXCEPTION(HttpStatus.UNAUTHORIZED, "A-004", "블랙리스트에 등록된 토큰입니다."),
	TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "A-005", "요청으로부터 토큰을 찾지 못했습니다."),
	REFRESH_TOKEN_NOT_MATCH(HttpStatus.BAD_REQUEST, "A-006", "리프레시 토큰이 일치하지 않습니다."),

	/**
	 * Menu Error
	 */
	MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "M-001", "메뉴를 찾을 수 없습니다."),
	MENU_ID_REQUIRED(HttpStatus.BAD_REQUEST, "M-002", "메뉴 ID가 필요합니다."),

	/**
	 * Visit Error
	 */
	VISIT_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "V-001", "방문 기록 저장에 실패했습니다."),
	INVALID_VISIT_REQUEST(HttpStatus.BAD_REQUEST, "V-002", "요청 데이터가 유효하지 않습니다."),
	LOCATION_REQUIRED(HttpStatus.BAD_REQUEST, "V-003", "위치 정보가 필요합니다."),
	COORDINATES_REQUIRED(HttpStatus.BAD_REQUEST, "V-004", "위도와 경도는 필수입니다."),
	LOCATION_OUT_OF_KOREA(HttpStatus.BAD_REQUEST, "V-005", "위치가 한국 내부가 아닙니다."),
	VISIT_NOT_FOUND(HttpStatus.NOT_FOUND, "V-006", "방문 기록을 찾을 수 없습니다."),
	VISIT_NOT_BELONG_TO_USER(HttpStatus.FORBIDDEN, "V-007", "해당 방문 기록에 대한 권한이 없습니다."),

	/**
	 * Review Error
	 */
	REVIEW_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "R-001", "리뷰 저장에 실패했습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
