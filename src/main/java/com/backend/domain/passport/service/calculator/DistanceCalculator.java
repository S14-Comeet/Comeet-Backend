package com.backend.domain.passport.service.calculator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.backend.common.util.GeoUtils;
import com.backend.domain.passport.entity.CountryCoordinate;
import com.backend.domain.passport.service.query.CountryCoordinateQueryService;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DistanceCalculator {

	private final Map<String, GeoPoint> countryCoordinates = new ConcurrentHashMap<>();

	private final CountryCoordinateQueryService queryService;

	@PostConstruct
	public void init() {
		List<CountryCoordinate> countries = queryService.findAll();
		for (CountryCoordinate c : countries) {
			countryCoordinates.put(c.getCountryName(), new GeoPoint(c.getLatitude(), c.getLongitude()));
		}
	}

	public double calculateTotalOriginDistance(List<String> route) {
		double totalOriginDistance = 0.0;

		for (int i = 0; i < route.size() - 1; i++) {
			String startCountry = route.get(i);
			String endCountry = route.get(i + 1);

			if (startCountry.equals(endCountry)) {
				continue;
			}

			GeoPoint p1 = countryCoordinates.get(startCountry);
			GeoPoint p2 = countryCoordinates.get(endCountry);

			if (p1 != null && p2 != null) {
				totalOriginDistance += GeoUtils.calculateHaversineDistance(p1.lat, p1.lon, p2.lat, p2.lon);
			}
		}
		return totalOriginDistance;
	}

	public record GeoPoint(Double lat, Double lon) {
	}
}
