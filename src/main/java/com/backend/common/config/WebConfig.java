package com.backend.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.backend.common.resolver.CurrentUserArgumentResolver;

import lombok.RequiredArgsConstructor;

/**
 * Spring MVC 관련 설정을 담당하는 Configuration 클래스
 *
 * <p>현재는 커스텀 ArgumentResolver를 등록하여
 * 컨트롤러에서 @CurrentUser 어노테이션을 통해
 * 인증된 사용자 정보를 주입받을 수 있도록 합니다.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final CurrentUserArgumentResolver currentUserArgumentResolver;

	/**
	 * 커스텀 Argument Resolver를 등록
	 *
	 * @param resolvers ArgumentResolver 리스트
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(currentUserArgumentResolver);
	}
}
