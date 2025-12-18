package com.backend.common.util;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtils {
	/**
	 * "HH:mm-HH:mm" 형식의 문자열을 파싱하여 LocalTime 배열로 반환합니다.
	 * 형식이 맞지 않거나 null인 경우 null을 반환합니다.
	 */
	public LocalTime[] parseOpeningHours(final String openingHours) {
		if (openingHours == null || openingHours.isBlank()) {
			return null;
		}

		String[] parts = openingHours.split("-");
		if (parts.length != 2) {
			return null;
		}

		try {
			LocalTime openTime = LocalTime.parse(parts[0].trim());
			LocalTime closeTime = LocalTime.parse(parts[1].trim());
			return new LocalTime[] {openTime, closeTime};
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/**
	 * LocalTime 배열에서 오픈 시간을 추출합니다.
	 */
	public LocalTime getOpenTime(final LocalTime[] times) {
		return (times != null && times.length == 2) ? times[0] : null;
	}

	/**
	 * LocalTime 배열에서 마감 시간을 추출합니다.
	 */
	public LocalTime getCloseTime(final LocalTime[] times) {
		return (times != null && times.length == 2) ? times[1] : null;
	}

	/**
	 * 오픈 시간과 마감 시간을 "HH:mm-HH:mm" 형식으로 포맷팅합니다.
	 */
	public static String formatOpeningHours(final LocalTime openTime, final LocalTime closeTime) {
		if (openTime == null || closeTime == null) {
			return null;
		}
		return openTime + "-" + closeTime;
	}
}