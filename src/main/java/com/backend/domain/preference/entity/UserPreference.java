package com.backend.domain.preference.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.domain.bean.enums.RoastingLevel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
