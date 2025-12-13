package com.backend.domain.review.converter;

import com.backend.domain.review.dto.response.CuppingResDto;
import com.backend.domain.review.entity.CuppingNote;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CuppingNoteConverter {

	public static CuppingResDto toCuppingResDto(final CuppingNote cuppingNote) {
		return new CuppingResDto(
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
