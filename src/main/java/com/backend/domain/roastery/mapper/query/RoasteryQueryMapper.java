package com.backend.domain.roastery.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import com.backend.domain.roastery.entity.Roastery;

@Mapper
public interface RoasteryQueryMapper {
	Optional<Roastery> findById(Long roasteryId);

	List<Roastery> findAll(Pageable pageable);

	int countAll();

	List<Roastery> findByNameContaining(String keyword, Pageable pageable);

	int countByNameContaining(String keyword);
}
