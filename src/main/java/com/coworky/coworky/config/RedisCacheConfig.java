package com.coworky.coworky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {

    /*
        RedisCacheManager 빈을 설정
        @param connectionFactory Spring Data Redis가 제공하는 Redis 연결 팩토리
        @return RedisCacheManager 인스턴스
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        // 1. 기본 캐시 설정 정의
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 캐시 항목 만료 시간 설정 (TTL)
                .entryTtl(Duration.ofMinutes(60))

                // 캐시 키(Key) 직렬화 설정
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )

                // 캐시 값(Value) 직렬화 설정
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))

                // 캐시 값에 null 허용 X
                .disableCachingNullValues();

        // 2. RedisCacheManager 빌드
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
}
