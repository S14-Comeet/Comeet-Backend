package com.backend.common.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig implements AsyncConfigurer {

	@Bean(name = "virtualThreadExecutor")
	@Override
	public Executor getAsyncExecutor() {
		log.info("[Async Config] Virtual Thread Executor 초기화");
		return Executors.newVirtualThreadPerTaskExecutor();
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) ->
			log.error("[Async Error] 비동기 작업 실패 - method: {}, params: {}", method.getName(), params, ex);
	}
}
