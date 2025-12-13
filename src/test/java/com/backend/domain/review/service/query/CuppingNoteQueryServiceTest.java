package com.backend.domain.review.service.query;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.common.error.exception.ReviewException;
import com.backend.domain.review.entity.CuppingNote;
import com.backend.domain.review.enums.RoastLevel;
import com.backend.domain.review.mapper.query.CuppingNoteQueryMapper;
import com.backend.domain.review.service.query.implement.CuppingNoteQueryServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("CuppingNoteQueryService 테스트")
class CuppingNoteQueryServiceTest {

	@Mock
	private CuppingNoteQueryMapper queryMapper;

	@InjectMocks
	private CuppingNoteQueryServiceImpl cuppingNoteQueryService;

	private CuppingNote testCuppingNote;
	private Long testReviewId;

	@BeforeEach
	void setUp() {
		testReviewId = 100L;
		testCuppingNote = CuppingNote.builder()
			.id(1L)
			.reviewId(testReviewId)
			.roastLevel(RoastLevel.MEDIUM)
			.fragranceScore(new BigDecimal("8.50"))
			.aromaScore(new BigDecimal("8.00"))
			.flavorScore(new BigDecimal("9.00"))
			.aftertasteScore(new BigDecimal("8.75"))
			.acidityScore(new BigDecimal("8.25"))
			.sweetnessScore(new BigDecimal("9.25"))
			.mouthfeelScore(new BigDecimal("8.50"))
			.totalScore(new BigDecimal("60.25"))
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	@Test
	@DisplayName("리뷰 ID로 커핑 노트 조회 성공")
	void findByReviewId_Success() {
		// given
		when(queryMapper.findByReviewId(testReviewId)).thenReturn(Optional.of(testCuppingNote));

		// when
		CuppingNote result = cuppingNoteQueryService.findByReviewId(testReviewId);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getReviewId()).isEqualTo(testReviewId);
		assertThat(result.getRoastLevel()).isEqualTo(RoastLevel.MEDIUM);
		verify(queryMapper, times(1)).findByReviewId(testReviewId);
	}

	@Test
	@DisplayName("리뷰 ID로 커핑 노트 조회 실패 - 존재하지 않음")
	void findByReviewId_NotFound() {
		// given
		when(queryMapper.findByReviewId(testReviewId)).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> cuppingNoteQueryService.findByReviewId(testReviewId))
			.isInstanceOf(ReviewException.class)
			.hasMessageContaining("커핑 노트를 찾을 수 없습니다.");

		verify(queryMapper, times(1)).findByReviewId(testReviewId);
	}

	@Test
	@DisplayName("리뷰 ID로 커핑 노트 존재 여부 확인 - 존재함")
	void existsByReviewId_True() {
		// given
		when(queryMapper.existsByReviewId(testReviewId)).thenReturn(true);

		// when
		boolean result = cuppingNoteQueryService.existsByReviewId(testReviewId);

		// then
		assertThat(result).isTrue();
		verify(queryMapper, times(1)).existsByReviewId(testReviewId);
	}

	@Test
	@DisplayName("리뷰 ID로 커핑 노트 존재 여부 확인 - 존재하지 않음")
	void existsByReviewId_False() {
		// given
		when(queryMapper.existsByReviewId(testReviewId)).thenReturn(false);

		// when
		boolean result = cuppingNoteQueryService.existsByReviewId(testReviewId);

		// then
		assertThat(result).isFalse();
		verify(queryMapper, times(1)).existsByReviewId(testReviewId);
	}
}
