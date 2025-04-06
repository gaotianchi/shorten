package com.github.gaotianchi.shorten.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author gaotianchi
 * @since 2025/4/6 上午9:29
 **/
@Configuration
public class CaffeineConfig {

    @Bean
    public Cache<String, String> shortLinkCache() {
        return Caffeine
                .newBuilder()
                .initialCapacity(1000)
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .recordStats()
                .build();
    }
}
