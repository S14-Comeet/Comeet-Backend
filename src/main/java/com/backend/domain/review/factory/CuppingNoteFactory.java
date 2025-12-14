package com.backend.domain.review.factory;

import org.springframework.stereotype.Component;

import com.backend.domain.review.dto.request.CuppingNoteReqDto;
import com.backend.domain.review.entity.CuppingNote;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CuppingNoteFactory {

	public CuppingNote create(final Long reviewId, final CuppingNoteReqDto reqDto) {
		return CuppingNote.builder()
			.reviewId(reviewId)
			.roastingLevel(reqDto.roastingLevel())
			.fragranceScore(reqDto.fragranceScore())
			.aromaScore(reqDto.aromaScore())
			.flavorScore(reqDto.flavorScore())
			.aftertasteScore(reqDto.aftertasteScore())
			.acidityScore(reqDto.acidityScore())
			.sweetnessScore(reqDto.sweetnessScore())
			.mouthfeelScore(reqDto.mouthfeelScore())
			.fragranceAromaDetail(reqDto.fragranceAromaDetail())
			.flavorAftertasteDetail(reqDto.flavorAftertasteDetail())
			.acidityNotes(reqDto.acidityNotes())
			.sweetnessNotes(reqDto.sweetnessNotes())
			.mouthfeelNotes(reqDto.mouthfeelNotes())
			.overallNotes(reqDto.overallNotes())
			.build();
	}

	public CuppingNote createForUpdate(final Long id, final Long reviewId, final CuppingNoteReqDto reqDto) {

		return CuppingNote.builder()
			.id(id)
			.reviewId(reviewId)
			.roastingLevel(reqDto.roastingLevel())
			.fragranceScore(reqDto.fragranceScore())
			.aromaScore(reqDto.aromaScore())
			.flavorScore(reqDto.flavorScore())
			.aftertasteScore(reqDto.aftertasteScore())
			.acidityScore(reqDto.acidityScore())
			.sweetnessScore(reqDto.sweetnessScore())
			.mouthfeelScore(reqDto.mouthfeelScore())
			.fragranceAromaDetail(reqDto.fragranceAromaDetail())
			.flavorAftertasteDetail(reqDto.flavorAftertasteDetail())
			.acidityNotes(reqDto.acidityNotes())
			.sweetnessNotes(reqDto.sweetnessNotes())
			.mouthfeelNotes(reqDto.mouthfeelNotes())
			.overallNotes(reqDto.overallNotes())
			.build();
	}
}
