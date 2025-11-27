package com.backend.domain.user.mapper.query;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.backend.common.mapper.QueryMapper;
import com.backend.domain.user.entity.User;

@Mapper
public interface UserQueryMapper extends QueryMapper<User> {
	Optional<User> findById(Long userId);

	Optional<User> findByEmail(String email);

	Optional<User> findBySocialId(String socialId);

	Boolean existBySocialId(String socialId);

	Boolean existByNickName(String nickname);
}
