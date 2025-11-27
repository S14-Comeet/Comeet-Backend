package com.backend.domain.auth.dto.request;

import com.backend.domain.user.entity.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRoleRequest {

	@NotNull(message = "Role is required")
	private Role role;
}
