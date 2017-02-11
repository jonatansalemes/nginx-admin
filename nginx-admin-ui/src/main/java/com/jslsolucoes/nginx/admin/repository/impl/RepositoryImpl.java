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

import javax.persistence.Id;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;

import com.jslsolucoes.nginx.admin.error.NginxAdminRuntimeException;

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

	public OperationType insert(T entity) {
		this.session.persist(entity);
		return OperationType.INSERT;
	}

	public OperationType update(T entity) {
		this.session.merge(entity);
		return OperationType.UPDATE;
	}

	private Long id(T entity) {
		List<Field> fields = new Mirror().on(clazz).reflectAll().fields().matching(new Matcher<Field>() {
			@Override
			public boolean accepts(Field field) {
				return field.isAnnotationPresent(Id.class);
			}
		});
		if (CollectionUtils.isEmpty(fields)) {
			throw new NginxAdminRuntimeException("Class" + this.clazz + " doesn't have @Id annotation");
		}
		return (Long) new Mirror().on(entity).invoke().getterFor(fields.get(0));
	}

	public OperationType delete(Long id) {
		return delete(load(id));
	}

	public OperationType delete(T entity) {
		this.session.delete(load(entity));
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
		Criteria criteria = session.createCriteria(clazz);
		criteria.setProjection(Property.forName("id"));
		return ((Long) criteria.uniqueResult()).intValue();
	}

	public void flushAndClear() {
		flush();
		clear();
	}

	public void flush() {
		this.session.flush();
	}

	public void clear() {
		this.session.clear();
	}

}