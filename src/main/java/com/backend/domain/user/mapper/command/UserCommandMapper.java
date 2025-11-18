package com.backend.domain.user.mapper.command;

import org.apache.ibatis.annotations.Mapper;

import com.backend.common.mapper.CommandMapper;
import com.backend.domain.user.entity.User;

@Mapper
public interface UserCommandMapper extends CommandMapper<User> {
	User insert(User user);
}
