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

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.nginx.admin.repository.MailRepository;
import com.jslsolucoes.nginx.admin.repository.UserRepository;

@RequestScoped
public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {

	private MailRepository mailRepository;

	public UserRepositoryImpl() {
		//Default constructor
	}

	@Inject
	public UserRepositoryImpl(Session session, MailRepository mailRepository) {
		super(session);
		this.mailRepository = mailRepository;
	}

	@Override
	public User authenticate(User user) {
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("login", user.getLogin()));
		criteria.add(Restrictions.eq("password", DigestUtils.sha256Hex(user.getPassword())));
		return (User) criteria.uniqueResult();
	}

	@Override
	public List<String> validateBeforeResetPassword(User user) {
		List<String> errors = new ArrayList<>();
		if (getByLogin(user) == null) {
			errors.add(Messages.getString("invalid.login"));
		}
		return errors;
	}

	private User getByLogin(User user) {
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("login", user.getLogin()));
		return (User) criteria.uniqueResult();
	}

	@Override
	public void resetPassword(User user) {
		String password = RandomStringUtils.randomAlphanumeric(8);
		User userToReset = getByLogin(user);
		userToReset.setPassword(DigestUtils.sha256Hex(password));
		userToReset.setPasswordForceChange(1);
		mailRepository.send(Messages.getString("reset.mail.subject"), userToReset.getLogin(),
				Messages.getString("reset.mail.body", userToReset.getLogin(), password));
	}

	@Override
	public List<String> validateBeforeChangePassword(User userParam, String oldPassword, String password,
			String passwordConfirm) {
		List<String> errors = new ArrayList<>();
		User user = load(userParam);

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
		List<String> errors = new ArrayList<>();
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
		User userToChange = load(user);
		userToChange.setPasswordForceChange(0);
		userToChange.setPassword(DigestUtils.sha256Hex(password));
	}

	@Override
	public User loadForSession(User user) {
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("id", user.getId()));
		return (User) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listAll() {
		Criteria criteria = session.createCriteria(User.class);
		return criteria.list();
	}

	@Override
	public List<String> validateBeforeCreateAdministrator(String login, String loginConfirm, String password,
			String passwordConfirm) {

		List<String> errors = new ArrayList<>();
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
