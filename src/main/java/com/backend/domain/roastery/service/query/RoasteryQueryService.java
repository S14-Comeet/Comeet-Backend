package com.backend.domain.roastery.service.query;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.backend.domain.roastery.entity.Roastery;

public interface RoasteryQueryService {
	Roastery findById(Long roasteryId);

	List<Roastery> findAll(Pageable pageable);

	int countAll();

	List<Roastery> findByNameContaining(String keyword, Pageable pageable);

	int countByNameContaining(String keyword);

	List<Roastery> findByOwnerId(Long ownerId, Pageable pageable);

	int countByOwnerId(Long ownerId);
}
