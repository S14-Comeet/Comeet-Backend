package com.backend.common.auth.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.backend.common.auth.principal.AuthenticatedUser;
import com.backend.domain.user.entity.Role;
import com.backend.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User, AuthenticatedUser {

	private final User user;

	@Override
	public String getId() {
		return user.getSocialId();
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public Role getRole() {
		return user.getRole();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return Map.of();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();

		collection.add((GrantedAuthority)() -> user.getRole().name());
		return collection;
	}

	@Override
	public String getName() {
		return user.getName();
	}
}
