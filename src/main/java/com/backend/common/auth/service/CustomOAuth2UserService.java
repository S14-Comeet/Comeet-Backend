package com.backend.common.auth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.common.auth.dto.CustomOAuth2User;
import com.backend.common.auth.dto.OAuth2Attribute;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.service.command.UserCommandService;
import com.backend.domain.user.service.query.UserQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private static final String UNDER_LINE = "_";

	private final UserQueryService userQueryService;
	private final UserCommandService userCommandService;

	@Override
	@Transactional
	public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, oAuth2User.getAttributes());

		String socialId = generateSocialId(registrationId, oAuth2Attribute);

		User user = findOrCreate(oAuth2Attribute, socialId);

		return new CustomOAuth2User(user);
	}

	private static String generateSocialId(final String registrationId, final OAuth2Attribute oAuth2Attribute) {
		return registrationId + UNDER_LINE + oAuth2Attribute.getProviderId();
	}

	private User findOrCreate(final OAuth2Attribute oAuth2Attribute, final String socialId) {
		return userQueryService.findBySocialId(socialId).orElseGet(() -> {
			User newUser = oAuth2Attribute.toEntity(socialId);
			return userCommandService.save(newUser);
		});
	}
}
