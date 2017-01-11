package com.excilys.formation.config;

import com.excilys.formation.model.util.PageFilter;
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

import java.lang.reflect.Method;
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
        GuavaCache cacheUsers = new GuavaCache("cacheUsers",
                CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).build());
        cacheManager.setCaches(Arrays.asList(cacheCompanies, cachePages, cacheUsers));
        return cacheManager;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {

            @Override
            public Object generate(Object arg0, Method arg1, Object... arg2) {
                StringBuilder sb = new StringBuilder();
                sb.append(arg1.getName());
                for (Object param : arg2) {
                    if (param != null) {
                        if (arg1.getName().equals("getPage")) {
                            PageFilter pageFilter = (PageFilter)param;
                            sb.append(pageFilter.getPageNum());
                            sb.append("&"+pageFilter.getElementsByPage());
                            if (!pageFilter.getConditions().isEmpty() && pageFilter.getConditions().containsKey("column")) {
                                sb.append(pageFilter.getConditions().get("column") + pageFilter.getConditions().get("order"));
                            }
                        }
                        else {
                            sb.append(param.toString());
                        }
                    }
                }
                return sb.toString();
            }};
    }
}