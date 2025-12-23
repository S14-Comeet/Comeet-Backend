package com.backend.domain.ai.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class PassportImageGeneratedEvent extends ApplicationEvent {

	private final String batchId;
	private final Long userId;
	private final Long passportId;
	private final String imageUrl;

	public PassportImageGeneratedEvent(
		Object source,
		String batchId,
		Long userId,
		Long passportId,
		String imageUrl
	) {
		super(source);
		this.batchId = batchId;
		this.userId = userId;
		this.passportId = passportId;
		this.imageUrl = imageUrl;
	}
}
