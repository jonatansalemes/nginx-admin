package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.nginx.admin.repository.MailRepository;
import com.jslsolucoes.nginx.admin.repository.UserRepository;

@RequestScoped
public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {

	private MailRepository mailRepository;

	public UserRepositoryImpl() {

	}

	@Inject
	public UserRepositoryImpl(EntityManager entityManager, MailRepository mailRepository) {
		super(entityManager);
		this.mailRepository = mailRepository;
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

	@Override
	public List<String> validateResetPassword(User user) {
		List<String> errors = new ArrayList<String>();
		if (getByLogin(user) == null) {
			errors.add(Messages.getString("invalid.login"));
		}
		return errors;
	}

	private User getByLogin(User user) {
		try {
			Query query = entityManager.createQuery("from User where login = :login");
			query.setParameter("login", user.getLogin());
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void resetPassword(User user) {
		String password = RandomStringUtils.randomAlphanumeric(8);
		user = getByLogin(user);
		user.setPassword(password);
		mailRepository.send(Messages.getString("reset.mail.subject"), user.getLogin(),
				Messages.getString("reset.mail.body", user.getLogin(), password));
	}

}
