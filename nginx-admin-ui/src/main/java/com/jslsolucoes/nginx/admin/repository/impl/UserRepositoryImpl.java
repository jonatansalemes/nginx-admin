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

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

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
			return (User) entityManager.createQuery("from User where login = :login and password = :password")
					.setParameter("login", user.getLogin())
					.setParameter("password", DigestUtils.sha256Hex(user.getPassword())).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<String> validateBeforeResetPassword(User user) {
		List<String> errors = new ArrayList<String>();
		if (getByLogin(user) == null) {
			errors.add(Messages.getString("invalid.login"));
		}
		return errors;
	}

	private User getByLogin(User user) {
		try {
			return (User) entityManager.createQuery("from User where login = :login")
					.setParameter("login", user.getLogin()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void resetPassword(User user) {
		String password = RandomStringUtils.randomAlphanumeric(8);
		user = getByLogin(user);
		user.setPassword(DigestUtils.sha256Hex(password));
		user.setPasswordForceChange(1);
		mailRepository.send(Messages.getString("reset.mail.subject"), user.getLogin(),
				Messages.getString("reset.mail.body", user.getLogin(), password));
	}

	@Override
	public List<String> validateBeforeChangePassword(User user, String oldPassword, String password,
			String passwordConfirm) {
		List<String> errors = new ArrayList<String>();
		user = load(user);

		errors.addAll(validatePasswordPair(password, passwordConfirm));

		if (!StringUtils.equals(user.getPassword(), DigestUtils.sha256Hex(oldPassword))) {
			errors.add(Messages.getString("invalid.password.old"));
		}

		if (StringUtils.equals(user.getPassword(), DigestUtils.sha256Hex(password))) {
			errors.add(Messages.getString("invalid.password.same"));
		}
		return errors;
	}

	private List<String> validatePasswordPair(String password, String passwordConfirm) {
		List<String> errors = new ArrayList<String>();
		Integer passwordSize = 8;
		if (password.length() < passwordSize) {
			errors.add(Messages.getString("invalid.password.size", passwordSize));
		}
		if (!StringUtils.equals(password, passwordConfirm)) {
			errors.add(Messages.getString("invalid.password.confirm"));
		}
		return errors;
	}

	@Override
	public void changePassword(User user, String password) {
		user = load(user);
		user.setPasswordForceChange(0);
		user.setPassword(DigestUtils.sha256Hex(password));
	}

	@Override
	public User loadForSession(User user) {
		return (User) entityManager.createQuery("from User where id = :id").setParameter("id", user.getId())
				.getSingleResult();
	}

	@Override
	public Boolean hasUsers() {
		return !CollectionUtils.isEmpty(entityManager.createQuery("from User").getResultList());
	}

	@Override
	public List<String> validateBeforeCreateAdministrator(String login, String loginConfirm, String password,
			String passwordConfirm) {

		List<String> errors = new ArrayList<String>();
		errors.addAll(validatePasswordPair(password, passwordConfirm));

		if (!StringUtils.equals(login, loginConfirm)) {
			errors.add(Messages.getString("admin.login.invalid.confirm"));
		}

		return errors;
	}

	@Override
	public void createAdministrator(String login, String password) {
		User user = new User();
		user.setPassword(DigestUtils.sha256Hex(password));
		user.setLogin(login);
		user.setPasswordForceChange(0);
		user.setAdmin(1);
		super.insert(user);
	}
}
