package com.backend.domain.beanscore.converter;

import com.backend.domain.beanscore.dto.response.BeanScoreResDto;
import com.backend.domain.beanscore.entity.BeanScore;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * BeanScore 엔티티 <-> DTO 변환
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BeanScoreConverter {

	public static BeanScoreResDto toResDto(BeanScore entity) {
		return new BeanScoreResDto(
			entity.getId(),
			entity.getBeanId(),
			entity.getAcidity(),
			entity.getBody(),
			entity.getSweetness(),
			entity.getBitterness(),
			entity.getAroma(),
			entity.getFlavor(),
			entity.getAftertaste(),
			entity.getTotalScore(),
			entity.getRoastLevel()
		);
	}
}
