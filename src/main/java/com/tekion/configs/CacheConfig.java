package com.tekion.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/*

This class has the configurations
for the Caffeine Cache we used.
 */
@Configuration
public class CacheConfig {
    @Value("${spring.cache.cache-names}")
    private String cacheName;
    @Value("${spring.cache.caffeine.spec.initialCapacity}")
    private int initialCapacity;
    @Value("${spring.cache.caffeine.spec.maximumSize}")
    private int maximumSize;
    @Value("${spring.cache.caffeine.spec.expireAfterAccess}")
    private int expireAfterAccess;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(cacheName);
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    Caffeine<Object,Object>caffeineCacheBuilder(){
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess, TimeUnit.SECONDS)
                .recordStats();
    }
}