package com.backend.domain.review.service.command;

import java.util.List;

public interface TastingNoteCommandService {
	void appendTastingNotes(Long reviewId, List<Long> flavorWheelIdList);
}
