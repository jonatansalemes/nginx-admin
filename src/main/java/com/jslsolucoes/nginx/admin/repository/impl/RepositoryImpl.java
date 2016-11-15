package com.jslsolucoes.nginx.admin.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.Id;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;

public abstract class RepositoryImpl<T> {

	protected Session session;
	private Class<T> clazz;

	public RepositoryImpl() {

	}

	@SuppressWarnings("unchecked")
	public RepositoryImpl(Session session) {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.session = session;
	}

	public List<T> listAll() {
		return listAll(null, null);
	}

	@SuppressWarnings("unchecked")
	public List<T> listAll(Integer firstResult, Integer maxResults) {
		Criteria criteria = session.createCriteria(clazz);
		if (firstResult != null && maxResults != null) {
			criteria.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return criteria.list();
	}

	public T load(T entity) {
		return load(id(entity));
	}

	@SuppressWarnings("unchecked")
	public T load(Long id) {
		return (T) session.load(clazz, id);
	}

	public void insert(T entity) {
		this.session.save(entity);
	}

	@SuppressWarnings("unchecked")
	public T update(T entity) {
		return (T) this.session.merge(entity);
	}

	private Long id(T entity) {
		Matcher<Field> matcher = new Matcher<Field>() {
			@Override
			public boolean accepts(Field field) {
				return field.isAnnotationPresent(Id.class);
			}
		};
		List<Field> fields = new Mirror().on(clazz).reflectAll().fields().matching(matcher);
		if (CollectionUtils.isEmpty(fields)) {
			try {
				throw new Exception("Class" + this.clazz + " doesn't have @Id annotation");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (Long) new Mirror().on(entity).invoke().getterFor(fields.get(0));
	}

	public OperationType delete(Long id) {
		return delete(load(id));
	}

	public OperationType delete(T entity) {
		this.session.delete(entity);
		return OperationType.DELETE;
	}

	public OperationResult saveOrUpdate(T entity) {
		Long id = id(entity);
		if (id == null) {
			this.insert(entity);
			return new OperationResult(OperationType.INSERT, id(entity));
		} else {
			this.update(entity);
			return new OperationResult(OperationType.UPDATE, id);
		}
	}

	public Integer count() {
		Criteria criteria = this.session.createCriteria(clazz);
		criteria.setProjection(Property.forName("id").count());
		return ((Long) criteria.uniqueResult()).intValue();
	}

	public void flush() {
		this.session.flush();
	}

	public void evict(T entity) {
		this.session.evict(entity);
	}
	
	public void clear(){
		this.session.clear();
	}

}