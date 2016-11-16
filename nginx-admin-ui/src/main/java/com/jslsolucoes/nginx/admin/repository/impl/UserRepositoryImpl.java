package com.jslsolucoes.nginx.admin.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.nginx.admin.repository.UserRepository;

@RequestScoped
public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {

	public UserRepositoryImpl() {
		
	}
	
	@Inject
	public UserRepositoryImpl(Session session) {
		super(session);
	}
	
	@Override
	public User authenticate(User user) {
		Criteria criteria = this.session.createCriteria(User.class);
		criteria.add(Restrictions.eq("login", user.getLogin()));
		criteria.add(Restrictions.eq("password", DigestUtils.sha256Hex(user.getPassword())));		
		return (User) criteria.uniqueResult();
	}

}
