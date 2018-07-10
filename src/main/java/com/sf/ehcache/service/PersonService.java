package com.sf.ehcache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sf.ehcache.dao.PersonDao;
import com.sf.ehcache.domain.Person;

@Service
public class PersonService {

	@Autowired
	private PersonDao personDao;
	
	/**
	 * 第一次运行方法，把结果放入helloworld缓存，key为'person' + id，后面都是不执行方法从缓存取
	 *	@ReturnType	Person 
	 *	@Date	2018年7月10日	下午3:14:42
	 *  @Param  @param person
	 *  @Param  @return
	 */
	@Cacheable(value="helloworld",key="'person' + #id")
	public Person insert(Person person){
		personDao.save(person);
		return person;
	}
	
	/**
	 * 第一次运行方法，把结果放入helloworld缓存，key为'person' + id，后面每次也会运行方法，并把结果重新放入缓存
	 *	@ReturnType	Person 
	 *	@Date	2018年7月10日	下午3:14:42
	 *  @Param  @param person
	 *  @Param  @return
	 */
	@CachePut(value = "helloworld", key="'person' + #person.getId()")
	public Person update(Person person){
		personDao.updateById(person);
		return person;
	}
	
	/**
	 * 从helloworld缓存中删除key为'person' + id的对象
	 *	@ReturnType	void 
	 *	@Date	2018年7月10日	下午3:43:20
	 *  @Param  @param id
	 */
	@CacheEvict(value = "helloworld", key="'person' + #id")
	public void delete(Long id){
		personDao.deleteById(id);
	}
	
	/**
	 * 第一次运行方法，把结果放入helloworld缓存，key为'person' + id，后面都是不执行方法从缓存取
	 *	@ReturnType	Person 
	 *	@Date	2018年7月10日	下午3:14:42
	 *  @Param  @param person
	 *  @Param  @return
	 */
	@Cacheable(value="helloworld",key="'person' + #id")
	public Person select(Long id){
		return personDao.selectById(id);
	}
	
	/**
	 * 第一次运行方法，把结果放入helloworld缓存，key为'persons'，后面都是不执行方法从缓存取
	 *	@ReturnType	Person 
	 *	@Date	2018年7月10日	下午3:14:42
	 *  @Param  @param person
	 *  @Param  @return
	 */
	@Cacheable(value="helloworld",key="'persons'")
	public List<Person> selectAll(){
		return personDao.selectAll();
	}
	
}
