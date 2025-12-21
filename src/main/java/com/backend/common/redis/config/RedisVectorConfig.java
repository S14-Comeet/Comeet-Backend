package com.backend.common.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPooled;

/**
 * Redis Vector Search 설정 (Jedis 기반)
 */
@Configuration
public class RedisVectorConfig {

	@Value("${spring.data.redis.host:localhost}")
	private String host;

	@Value("${spring.data.redis.port:6379}")
	private int port;

	@Value("${redis.vector.index-name:bean_embeddings}")
	private String indexName;

	@Value("${redis.vector.dimension:1536}")
	private int dimension;

	@Bean
	public JedisPooled jedisPooled() {
		return new JedisPooled(host, port);
	}

	@Bean
	public RedisVectorProperties redisVectorProperties() {
		return new RedisVectorProperties(indexName, dimension);
	}

	public record RedisVectorProperties(
		String indexName,
		int dimension
	) {
	}
}
