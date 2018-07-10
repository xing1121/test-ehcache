package com.sf.ehcache.dao;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sf.ehcache.domain.Person;

@Repository
public class PersonDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(Person person) {
		if (person == null) {
			return;
		}
		this.getSession().save(person);
	}

	public void updateById(Person person) {
		if (person == null) {
			return;
		}
		Long id = person.getId();
		Person p2 = this.selectById(id);
		if (p2 == null) {
			return;
		}
		try {
			BeanUtils.copyProperties(p2, person);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.getSession().update(p2);
	}

	public void deleteById(Long id) {
		if (id == null) {
			return;
		}
		Person p = new Person();
		p.setId(id);
		this.getSession().delete(p);
	}

	public Person selectById(Long id) {
		Object o = this.getSession().get(Person.class, id);
		if (o == null) {
			return null;
		}
		return (Person) o;
	}

	@SuppressWarnings("unchecked")
	public List<Person> selectAll() {
		String hql = "from Person";
		Query query = this.getSession().createQuery(hql);
		List<Person> list = query.list();
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list;
	}

	/**
	 * 获取session
	 * 
	 * @ReturnType Session
	 * @Date 2018年3月22日 下午3:01:13
	 * @Param @return
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

}
