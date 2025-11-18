package com.backend.common.mapper;

import java.util.Optional;

public interface QueryMapper<T> {
	Optional<T> findById(Long id);
}
