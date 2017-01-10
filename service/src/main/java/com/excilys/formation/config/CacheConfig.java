package com.excilys.formation.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Class configuring the cache.
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
    @Bean
    @Override
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        GuavaCache cacheCompanies = new GuavaCache("cacheCompanies",
                CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).build());
        GuavaCache cachePages = new GuavaCache("cachePages",
                CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).build());
        cacheManager.setCaches(Arrays.asList(cacheCompanies, cachePages));
        return cacheManager;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }
}