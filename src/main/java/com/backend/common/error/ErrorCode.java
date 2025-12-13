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
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-007", "유효하지 않은 토큰입니다."),
	INVALID_TOKEN_SIGNATURE(HttpStatus.UNAUTHORIZED, "A-008", "토큰 서명이 유효하지 않습니다."),
	TOKEN_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "A-009", "토큰 처리 중 오류가 발생했습니다."),

	/**
	 * Menu Error
	 */
	MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "M-001", "메뉴를 찾을 수 없습니다."),
	MENU_ID_REQUIRED(HttpStatus.BAD_REQUEST, "M-002", "메뉴 ID가 필요합니다."),

	/**
	 * Visit Error
	 */
	VISIT_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "V-001", "방문 기록 저장에 실패했습니다."),
	INVALID_VISIT_REQUEST(HttpStatus.BAD_REQUEST, "V-002", "방문 기록 요청 데이터가 유효하지 않습니다."),
	LOCATION_REQUIRED(HttpStatus.BAD_REQUEST, "V-003", "위치 정보가 필요합니다."),
	COORDINATES_REQUIRED(HttpStatus.BAD_REQUEST, "V-004", "위도와 경도는 필수입니다."),
	LOCATION_OUT_OF_KOREA(HttpStatus.BAD_REQUEST, "V-005", "위치가 한국 내부가 아닙니다."),
	VISIT_NOT_FOUND(HttpStatus.NOT_FOUND, "V-006", "방문 기록을 찾을 수 없습니다."),
	VISIT_NOT_BELONG_TO_USER(HttpStatus.FORBIDDEN, "V-007", "해당 방문 기록에 대한 권한이 없습니다."),

	/**
	 * Review Error
	 */
	INVALID_REVIEW_REQUEST(HttpStatus.BAD_REQUEST, "R-001", "리뷰 요청 데이터가 유효하지 않습니다."),
	REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "R-002", "리뷰 기록을 찾을 수 없습니다."),
	REVIEW_UPDATE_FAILED(HttpStatus.CONFLICT, "R-003", "리뷰 업데이트에 실패했습니다."),
	REVIEW_SOFT_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "R-004", "리뷰 SOFT DELETE에 실패했습니다."),
	ALREADY_DELETED_REVIEW(HttpStatus.BAD_REQUEST, "R-005", "이미 삭제 처리된 리뷰입니다."),
	REVIEW_ALREADY_EXISTS_FOR_VISIT(HttpStatus.CONFLICT, "R-006", "해당 방문에 대한 리뷰가 이미 존재합니다."),
	CUPPING_NOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "R-007", "커핑 노트를 찾을 수 없습니다."),
	CUPPING_NOTE_ALREADY_EXISTS(HttpStatus.CONFLICT, "R-008", "해당 리뷰에 대한 커핑 노트가 이미 존재합니다."),
	CUPPING_NOTE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "R-009", "커핑 노트 저장에 실패했습니다."),
	CUPPING_NOTE_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "R-010", "커핑 노트 업데이트에 실패했습니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
