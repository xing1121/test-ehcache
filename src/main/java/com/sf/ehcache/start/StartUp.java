package com.sf.ehcache.start;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sf.ehcache.domain.Person;
import com.sf.ehcache.service.PersonService;

public class StartUp {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:conf/spring-beans.xml");
		
		PersonService personService = context.getBean(PersonService.class);
		
		// 第一次从数据库查
		List<Person> persons = personService.selectAll();
		persons.forEach(System.out::println);
		// 第二次是缓存
		List<Person> persons2 = personService.selectAll();
		persons2.forEach(System.out::println);
		
		context.close();
	}
	
}
