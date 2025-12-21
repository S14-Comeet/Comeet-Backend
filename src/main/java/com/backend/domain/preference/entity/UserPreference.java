package com.backend.domain.preference.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 취향 엔티티
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPreference {

	private Long id;
	private Long userId;
	private Integer prefAcidity;
	private Integer prefBody;
	private Integer prefSweetness;
	private Integer prefBitterness;
	private List<RoastingLevel> preferredRoastLevels;
	private List<String> likedTags;
	private List<String> dislikedTags;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	/**
	 * 기본 취향으로 생성 (모든 값 5)
	 */
	public static UserPreference createDefault(Long userId) {
		return UserPreference.builder()
			.userId(userId)
			.prefAcidity(5)
			.prefBody(5)
			.prefSweetness(5)
			.prefBitterness(5)
			.preferredRoastLevels(List.of())
			.likedTags(List.of())
			.dislikedTags(List.of())
			.build();
	}

	/**
	 * 취향 업데이트
	 */
	public void update(
		Integer prefAcidity,
		Integer prefBody,
		Integer prefSweetness,
		Integer prefBitterness,
		List<RoastingLevel> preferredRoastLevels,
		List<String> likedTags,
		List<String> dislikedTags
	) {
		if (prefAcidity != null) {
			this.prefAcidity = prefAcidity;
		}
		if (prefBody != null) {
			this.prefBody = prefBody;
		}
		if (prefSweetness != null) {
			this.prefSweetness = prefSweetness;
		}
		if (prefBitterness != null) {
			this.prefBitterness = prefBitterness;
		}
		if (preferredRoastLevels != null) {
			this.preferredRoastLevels = preferredRoastLevels;
		}
		if (likedTags != null) {
			this.likedTags = likedTags;
		}
		if (dislikedTags != null) {
			this.dislikedTags = dislikedTags;
		}
	}
}
