package com.jslsolucoes.nginx.admin.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.codec.digest.DigestUtils;

import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.nginx.admin.repository.UserRepository;

@RequestScoped
public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {

	public UserRepositoryImpl() {

	}

	@Inject
	public UserRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public User authenticate(User user) {
		try {
			Query query = entityManager.createQuery("from User where login = :login and password = :password");
			query.setParameter("login", user.getLogin());
			query.setParameter("password", DigestUtils.sha256Hex(user.getPassword()));
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
