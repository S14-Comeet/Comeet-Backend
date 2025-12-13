package com.backend.domain.review.service.command;

import com.backend.domain.review.entity.CuppingNote;

public interface CuppingNoteCommandService {
	int insert(CuppingNote cuppingNote);

	int update(CuppingNote cuppingNote);
}
