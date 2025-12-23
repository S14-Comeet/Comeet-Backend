package com.backend.domain.ai.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash(value = "batch:progress", timeToLive = 86400)
public class BatchProgress implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private String batchId;

	private int total;

	@Builder.Default
	private AtomicInteger completed = new AtomicInteger(0);

	@Builder.Default
	private AtomicInteger failed = new AtomicInteger(0);

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	public void incrementCompleted() {
		this.completed.incrementAndGet();
	}

	public void incrementFailed() {
		this.failed.incrementAndGet();
	}

	public int getRemaining() {
		return total - completed.get() - failed.get();
	}

	public double getProgressPercentage() {
		if (total == 0) {
			return 0.0;
		}
		return (completed.get() + failed.get()) * 100.0 / total;
	}

	public boolean isCompleted() {
		return getRemaining() == 0;
	}

	public void markAsCompleted() {
		this.endTime = LocalDateTime.now();
	}

	public static BatchProgress init(final String batchId, final int totalUsers) {
		return BatchProgress.builder()
			.batchId(batchId)
			.total(totalUsers)
			.completed(new AtomicInteger(0))
			.failed(new AtomicInteger(0))
			.startTime(LocalDateTime.now())
			.build();
	}

}
