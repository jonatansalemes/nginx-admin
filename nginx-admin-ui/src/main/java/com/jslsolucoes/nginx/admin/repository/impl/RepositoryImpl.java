package com.jslsolucoes.nginx.admin.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.collections.CollectionUtils;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;

public abstract class RepositoryImpl<T> {

	protected EntityManager entityManager;
	private Class<T> clazz;

	public RepositoryImpl() {

	}

	@SuppressWarnings("unchecked")
	public RepositoryImpl(EntityManager entityManager) {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityManager = entityManager;
	}

	public List<T> listAll() {
		return listAll(null, null);
	}

	public List<T> listAll(Integer firstResult, Integer maxResults) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
		if (firstResult != null && maxResults != null) {
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}

	public T load(T entity) {
		return load(id(entity));
	}

	public T load(Long id) {
		return (T) entityManager.find(clazz, id);
	}

	public OperationType insert(T entity) {
		this.entityManager.persist(entity);
		return OperationType.INSERT;
	}

	public OperationType update(T entity) {
		this.entityManager.merge(entity);
		return OperationType.UPDATE;
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
		this.entityManager.remove(load(entity));
		return OperationType.DELETE;
	}

	public OperationResult saveOrUpdate(T entity) {
		Long id = id(entity);
		if (id == null) {
			return new OperationResult(insert(entity), id(entity));
		} else {
			return new OperationResult(update(entity), id);
		}
	}

	public Long count() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		criteriaQuery.select(builder.count(criteriaQuery.from(clazz).get("id")));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	public void flushAndClear() {
		flush();
		clear();
	}

	public void flush() {
		this.entityManager.flush();
	}

	public void clear() {
		this.entityManager.clear();
	}

}