package com.backend.common.ai.service;

import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenAI Embedding 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmbeddingService {

	private final EmbeddingModel embeddingModel;

	/**
	 * 텍스트를 임베딩 벡터로 변환
	 *
	 * @param text 임베딩할 텍스트
	 * @return 임베딩 벡터 (1536 차원)
	 */
	public float[] embed(String text) {
		log.debug("Embedding text: {}", text);

		EmbeddingRequest request = new EmbeddingRequest(
			List.of(text),
			OpenAiEmbeddingOptions.builder()
				.withModel("text-embedding-3-small")
				.build()
		);

		EmbeddingResponse response = embeddingModel.call(request);
		return response.getResult().getOutput();
	}

	/**
	 * 태그 리스트를 임베딩 벡터로 변환
	 *
	 * @param tags 플레이버 태그 리스트
	 * @return 임베딩 벡터
	 */
	public float[] embedTags(List<String> tags) {
		if (tags == null || tags.isEmpty()) {
			log.warn("Empty tags provided for embedding");
			return new float[1536];
		}
		String text = String.join(", ", tags);
		return embed(text);
	}

	/**
	 * 여러 텍스트를 배치로 임베딩
	 *
	 * @param texts 임베딩할 텍스트 리스트
	 * @return 임베딩 벡터 리스트
	 */
	public List<float[]> embedBatch(List<String> texts) {
		log.debug("Batch embedding {} texts", texts.size());

		EmbeddingRequest request = new EmbeddingRequest(
			texts,
			OpenAiEmbeddingOptions.builder()
				.withModel("text-embedding-3-small")
				.build()
		);

		EmbeddingResponse response = embeddingModel.call(request);
		return response.getResults().stream()
			.map(result -> result.getOutput())
			.toList();
	}
}
