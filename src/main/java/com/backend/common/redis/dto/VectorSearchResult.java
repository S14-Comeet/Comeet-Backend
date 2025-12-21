package com.backend.common.redis.dto;

/**
 * Redis Vector 검색 결과 DTO
 */
public record VectorSearchResult(
	Long beanId,
	Double score
) {
}
