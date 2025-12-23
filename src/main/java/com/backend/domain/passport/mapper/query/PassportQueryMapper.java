package com.backend.domain.passport.mapper.query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.backend.domain.passport.entity.Passport;

@Mapper
public interface PassportQueryMapper {

	Passport findById(@Param("passportId") Long passportId);

	List<Passport> findAllByUserIdAndYear(@Param("userId") Long userId, @Param("year") Integer year);

	List<Long> findUsersWithVisitsInMonth(@Param("year") int year, @Param("month") int month);

	Optional<Passport> findByUserIdAndYearAndMonth(
		@Param("userId") Long userId,
		@Param("year") int year,
		@Param("month") int month
	);

	@MapKey("")
	List<Map<String, Object>> findVisitsWithMenuInMonth(
		@Param("userId") Long userId,
		@Param("year") int year,
		@Param("month") int month
	);

	Optional<Passport> findLatestByUserId(@Param("userId") Long userId);
}
