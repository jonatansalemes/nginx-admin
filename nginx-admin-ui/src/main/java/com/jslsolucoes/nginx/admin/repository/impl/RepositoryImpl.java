/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;

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

	@SuppressWarnings("unchecked")
	public List<T> listAll(Integer firstResult, Integer maxResults) {
		Query query = entityManager.createQuery("from " + clazz.getSimpleName());
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

	public OperationType delete(Long id) throws Exception {
		return delete(load(id));
	}

	public OperationType delete(T entity) throws Exception {
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

	public Integer count() {
		Query query = entityManager.createQuery("select count(id) from " + clazz.getSimpleName());
		return ((Long) query.getSingleResult()).intValue();
	}
	
	public void flushAndClear(){
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