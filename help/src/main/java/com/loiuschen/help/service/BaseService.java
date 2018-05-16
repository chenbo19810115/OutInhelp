package com.loiuschen.help.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	public <T> List<T> list(Class<?> entityClass) {
		return (List<T>) hibernateTemplate.loadAll(entityClass);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<?> entityClass, Serializable id) {
		return (T) hibernateTemplate.get(entityClass, id);
	}
}
