package com.sf.ehcache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;

/**
 * 描述：使用@Configuration @EnableCaching 这两个标签和以下的方法，可以代替ehcache的xml配置文件
 * @author 80002888
 * @date   2018年7月10日
 */
@Configuration
@EnableCaching
public class CacheConfig {
	
	@Bean
    public CacheManager cacheManager() {
    	// 创建EHCache缓存
    	net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.create();
    	// 创建缓存配置
		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		cacheConfiguration.setName("helloworld");
		cacheConfiguration.setCopyOnWrite(true);
		cacheConfiguration.setTimeToLiveSeconds(60*60*24);
		cacheConfiguration.setMaxBytesLocalHeap(1024 * 1024 * 10L);
		// 新建缓存
		Cache cache = new Cache(cacheConfiguration);
		// 缓存添加到缓存管理器中
		cacheManager.addCache(cache);
		return new EhCacheCacheManager(cacheManager);
    }
    
}
