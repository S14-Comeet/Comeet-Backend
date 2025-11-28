package com.backend.domain.auth.service.query;

import com.backend.domain.auth.dto.response.CheckNicknameDuplicateResponse;

public interface AuthQueryService {
	CheckNicknameDuplicateResponse checkNicknameDuplicate(final String nickname);
}
