package com.backend.domain.passport.service.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.Builder;
import lombok.Getter;

@Service
public class PassportStatisticsCalculator {

	public PassportStatistics calculate(List<Map<String, Object>> visits) {
		if (CollectionUtils.isEmpty(visits)) {
			return PassportStatistics.empty();
		}

		Set<Long> uniqueStores = new HashSet<>();
		Set<Long> uniqueBeans = new HashSet<>();
		Map<String, Integer> originCounts = new HashMap<>();
		Map<String, Integer> roasteryCounts = new HashMap<>();
		List<String> originSequence = new ArrayList<>();

		for (Map<String, Object> visit : visits) {
			processVisit(visit, uniqueStores, uniqueBeans, originCounts, roasteryCounts, originSequence);
		}

		return PassportStatistics.of(visits, uniqueStores, uniqueBeans, findTopEntry(originCounts),
			findTopEntry(roasteryCounts), originSequence);
	}

	private void processVisit(
		Map<String, Object> visit,
		Set<Long> uniqueStores,
		Set<Long> uniqueBeans,
		Map<String, Integer> originCounts,
		Map<String, Integer> roasteryCounts,
		List<String> originSequence
	) {
		Long storeId = (Long)visit.get("store_id");
		Long beanId = (Long)visit.get("bean_id");
		String origin = (String)visit.get("origin");
		String roasteryName = (String)visit.get("roastery_name");

		if (storeId != null) {
			uniqueStores.add(storeId);
		}

		if (beanId != null) {
			uniqueBeans.add(beanId);
		}

		if (origin != null && !origin.isEmpty()) {
			originCounts.put(origin, originCounts.getOrDefault(origin, 0) + 1);
			originSequence.add(origin);
		}

		if (roasteryName != null && !roasteryName.isEmpty()) {
			roasteryCounts.put(roasteryName, roasteryCounts.getOrDefault(roasteryName, 0) + 1);
		}
	}

	private String findTopEntry(Map<String, Integer> countMap) {
		return countMap.entrySet().stream()
			.max(Map.Entry.comparingByValue())
			.map(Map.Entry::getKey)
			.orElse(null);
	}

	@Getter
	@Builder
	public static class PassportStatistics {
		private final int totalCoffeeCount;
		private final int totalStoreCount;
		private final int totalBeanCount;
		private final String topOrigin;
		private final String topRoastery;
		private final List<String> originSequence;

		public static PassportStatistics empty() {
			return PassportStatistics.builder()
				.totalCoffeeCount(0)
				.totalStoreCount(0)
				.totalBeanCount(0)
				.topOrigin(null)
				.topRoastery(null)
				.originSequence(Collections.emptyList())
				.build();
		}

		public static PassportStatistics of(
			final List<Map<String, Object>> visits,
			final Set<Long> uniqueStores,
			final Set<Long> uniqueBeans,
			final String originCounts,
			final String roasteryCounts,
			final List<String> originSequence
		) {
			return PassportStatistics.builder()
				.totalCoffeeCount(visits.size())
				.totalStoreCount(uniqueStores.size())
				.totalBeanCount(uniqueBeans.size())
				.topOrigin(originCounts)
				.topRoastery(roasteryCounts)
				.originSequence(originSequence)
				.build();
		}
	}
}
