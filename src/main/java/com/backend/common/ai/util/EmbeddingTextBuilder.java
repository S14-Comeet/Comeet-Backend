package com.backend.common.ai.util;

import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

/**
 * 임베딩용 텍스트 생성 유틸리티
 *
 * 점수, 플레이버 태그, 로스팅 레벨을 조합하여 풍부한 임베딩 텍스트 생성
 */
public final class EmbeddingTextBuilder {

	private EmbeddingTextBuilder() {
	}

	/**
	 * 원두용 임베딩 텍스트 생성
	 *
	 * @param acidity      산미 점수 (1-10)
	 * @param body         바디감 점수 (1-10)
	 * @param sweetness    단맛 점수 (1-10)
	 * @param bitterness   쓴맛 점수 (1-10)
	 * @param roastLevel   로스팅 레벨
	 * @param flavorPaths  플레이버 계층 경로 목록 (예: "Fruity > Citrus > Lemon")
	 * @return 임베딩용 텍스트
	 */
	public static String buildBeanEmbeddingText(
		Integer acidity,
		Integer body,
		Integer sweetness,
		Integer bitterness,
		RoastingLevel roastLevel,
		List<String> flavorPaths
	) {
		StringBuilder sb = new StringBuilder();

		// 맛 프로필 (하이브리드: 숫자 + 레벨)
		sb.append("Taste Profile: ");
		sb.append("Acidity ").append(formatScore(acidity)).append(", ");
		sb.append("Body ").append(formatScore(body)).append(", ");
		sb.append("Sweetness ").append(formatScore(sweetness)).append(", ");
		sb.append("Bitterness ").append(formatScore(bitterness)).append(". ");

		// 로스팅 레벨
		if (roastLevel != null) {
			sb.append("Roast Level: ").append(roastLevelToText(roastLevel)).append(". ");
		}

		// 플레이버 노트
		if (flavorPaths != null && !flavorPaths.isEmpty()) {
			sb.append("Flavor Notes: ");
			sb.append(String.join(", ", flavorPaths));
			sb.append(".");
		}

		return sb.toString();
	}

	/**
	 * 사용자 취향용 임베딩 텍스트 생성
	 *
	 * @param prefAcidity           선호 산미 (1-10)
	 * @param prefBody              선호 바디감 (1-10)
	 * @param prefSweetness         선호 단맛 (1-10)
	 * @param prefBitterness        선호 쓴맛 (1-10)
	 * @param preferredRoastLevels  선호 로스팅 레벨 목록
	 * @param likedFlavorPaths      선호 플레이버 계층 경로 목록
	 * @return 임베딩용 텍스트
	 */
	public static String buildUserPreferenceEmbeddingText(
		Integer prefAcidity,
		Integer prefBody,
		Integer prefSweetness,
		Integer prefBitterness,
		List<RoastingLevel> preferredRoastLevels,
		List<String> likedFlavorPaths
	) {
		StringBuilder sb = new StringBuilder();

		// 선호 맛 프로필 (하이브리드: 숫자 + 레벨)
		sb.append("Preferred Taste Profile: ");
		sb.append("Acidity ").append(formatScore(prefAcidity)).append(", ");
		sb.append("Body ").append(formatScore(prefBody)).append(", ");
		sb.append("Sweetness ").append(formatScore(prefSweetness)).append(", ");
		sb.append("Bitterness ").append(formatScore(prefBitterness)).append(". ");

		// 선호 로스팅 레벨
		if (preferredRoastLevels != null && !preferredRoastLevels.isEmpty()) {
			sb.append("Preferred Roast Levels: ");
			List<String> roastTexts = preferredRoastLevels.stream()
				.map(EmbeddingTextBuilder::roastLevelToText)
				.toList();
			sb.append(String.join(", ", roastTexts));
			sb.append(". ");
		}

		// 선호 플레이버 노트
		if (likedFlavorPaths != null && !likedFlavorPaths.isEmpty()) {
			sb.append("Preferred Flavor Notes: ");
			sb.append(String.join(", ", likedFlavorPaths));
			sb.append(".");
		}

		return sb.toString();
	}

	/**
	 * 점수를 하이브리드 형식으로 변환 (숫자 + 레벨)
	 *
	 * @param score 점수 (1-10)
	 * @return "8/10 (High)" 형식
	 */
	private static String formatScore(Integer score) {
		if (score == null) {
			score = 5;
		}
		return score + "/10 (" + scoreToLevel(score) + ")";
	}

	/**
	 * 점수를 레벨 텍스트로 변환
	 *
	 * @param score 점수 (1-10)
	 * @return "Very Low", "Low", "Medium", "High", "Very High"
	 */
	private static String scoreToLevel(Integer score) {
		if (score == null) {
			return "Medium";
		}
		if (score <= 2) {
			return "Very Low";
		} else if (score <= 4) {
			return "Low";
		} else if (score <= 6) {
			return "Medium";
		} else if (score <= 8) {
			return "High";
		} else {
			return "Very High";
		}
	}

	/**
	 * 로스팅 레벨을 텍스트로 변환
	 */
	private static String roastLevelToText(RoastingLevel level) {
		if (level == null) {
			return "Medium Roast";
		}
		return switch (level) {
			case LIGHT -> "Light Roast";
			case MEDIUM_LIGHT -> "Medium-Light Roast";
			case MEDIUM -> "Medium Roast";
			case MEDIUM_DARK -> "Medium-Dark Roast";
			case DARK, HEAVY -> "Dark Roast";
		};
	}
}
