package com.sf.ehcache.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

public class TestEhcache {

	private Logger logger = LoggerFactory.getLogger(TestEhcache.class);
	
	/**
	 * 动态创建Cache
	 *	@ReturnType	void 
	 *	@Date	2018年7月10日	上午11:22:05
	 *  @Param
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test2(){
		// 使用Ehcache默认配置获取单例的CacheManager实例
		CacheManager cacheManager = CacheManager.create();
		// 缓存配置
		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		// 缓存名称
		cacheConfiguration.setName("MyCache");
		// 写入时复制
		cacheConfiguration.setCopyOnWrite(true);
		// 生存时间
		cacheConfiguration.setTimeToLiveSeconds(60*60*24);
		// 设置堆中最多10M
		cacheConfiguration.setMaxBytesLocalHeap(1024 * 1024 * 10L);
		// 新建缓存
		Cache cache = new Cache(cacheConfiguration);
		// 缓存添加到缓存管理器中
		cacheManager.addCache(cache);
		
		// 缓存中添加对象
		cache.put(new Element("key01", Arrays.asList("A","B","C")));
		// 获取缓存中对象
		List<String> list = (List<String>) cache.get("key01").getObjectValue();
		list.stream().forEach(System.out::println);
	}
	
	/**
	 * 使用配置中的Cache
	 *	@ReturnType	void 
	 *	@Date	2018年7月10日	上午11:21:50
	 *  @Param
	 */
	@Test
	public void test1(){
		logger.info("#################main方法运行#################");
		// 创建一个CacheManager，指定配置文件位置为类路径下的/ehcache/ehcache.xml
		CacheManager cacheManager = CacheManager.newInstance(this.getClass().getResource("/ehcache/ehcache.xml"));
		
		// 通过CacheManager获取在xml中配置的名字为helloworld的缓存
		Cache cache = cacheManager.getCache("helloworld");
		
		// 创建Element(k=v)
		Element element = new Element("greeting", "Hello, World!");
		// Element放入helloworld缓存
		cache.put(element);
		
		// 获取Element，获取Element的value
		System.out.println(cache.get("greeting").getObjectValue());
		
		// 从缓存中删除对象
		cache.remove("greeting");
		
		// 再次获取为null
		System.out.println(cache.get("greeting"));
	}
}
