package com.backend.domain.review.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.domain.review.enums.RoastLevel;

public record CuffingResDto(Long id, RoastLevel roastLevel, BigDecimal fragranceScore, BigDecimal aromaScore,
							BigDecimal flavorScore, BigDecimal aftertasteScore, BigDecimal acidityScore,
							BigDecimal sweetnessScore, BigDecimal mouthfeelScore, BigDecimal totalScore,
							String fragranceAromaDetail, String flavorAftertasteDetail, String acidityNotes,
							String sweetnessNotes, String mouthfeelNotes, String overallNotes, LocalDateTime createdAt)
	implements Serializable {
}