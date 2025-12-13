package com.backend.domain.review.converter;

import com.backend.domain.review.dto.response.CuffingResDto;
import com.backend.domain.review.entity.CuppingNote;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CuppingNoteConverter {

	public static CuffingResDto toCuffingResDto(final CuppingNote cuppingNote) {
		return new CuffingResDto(
			cuppingNote.getId(),
			cuppingNote.getRoastLevel(),
			cuppingNote.getFragranceScore(),
			cuppingNote.getAromaScore(),
			cuppingNote.getFlavorScore(),
			cuppingNote.getAftertasteScore(),
			cuppingNote.getAcidityScore(),
			cuppingNote.getSweetnessScore(),
			cuppingNote.getMouthfeelScore(),
			cuppingNote.getTotalScore(),
			cuppingNote.getFragranceAromaDetail(),
			cuppingNote.getFlavorAftertasteDetail(),
			cuppingNote.getAcidityNotes(),
			cuppingNote.getSweetnessNotes(),
			cuppingNote.getMouthfeelNotes(),
			cuppingNote.getOverallNotes(),
			cuppingNote.getCreatedAt()
		);
	}
}
