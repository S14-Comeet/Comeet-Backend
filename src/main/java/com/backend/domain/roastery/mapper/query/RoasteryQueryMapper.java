package com.backend.domain.roastery.mapper.query;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.backend.domain.roastery.entity.Roastery;

@Mapper
public interface RoasteryQueryMapper {
	Optional<Roastery> findById(@Param("roasteryId") Long roasteryId);

	List<Roastery> findAll(@Param("pageable") Pageable pageable);

	int countAll();

	List<Roastery> findByNameContaining(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

	int countByNameContaining(@Param("keyword") String keyword);

	List<Roastery> findByOwnerId(@Param("ownerId") Long ownerId, @Param("pageable") Pageable pageable);

	int countByOwnerId(@Param("ownerId") Long ownerId);
}
