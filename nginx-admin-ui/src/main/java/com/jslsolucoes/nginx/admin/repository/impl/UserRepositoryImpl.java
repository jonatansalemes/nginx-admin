package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.jslsolucoes.i18n.Messages;
import com.jslsolucoes.mail.service.MailService;
import com.jslsolucoes.nginx.admin.model.User;
import com.jslsolucoes.nginx.admin.model.User_;
import com.jslsolucoes.nginx.admin.repository.UserRepository;

@RequestScoped
public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {

	private MailService mailService;

	@Deprecated
	public UserRepositoryImpl() {
		
	}

	@Inject
	public UserRepositoryImpl(EntityManager entityManager, MailService mailService) {
		super(entityManager);
		this.mailService = mailService;
	}

	@Override
	public User authenticate(String identification,String password) {
		try {
		    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		    Root<User> root = criteriaQuery.from(User.class);
		    criteriaQuery.where(
		    		criteriaBuilder.and(
		    				criteriaBuilder.or(
		    							criteriaBuilder.equal(root.get(User_.login), identification),
		    							criteriaBuilder.equal(root.get(User_.email), identification)
		    				),
		    				criteriaBuilder.equal(root.get(User_.password), DigestUtils.sha256Hex(password))
		    		)
		    );
		    return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
		    return null;
		}
	}

	@Override
	public List<String> validateBeforeResetPasswordFor(String identification) {
		List<String> errors = new ArrayList<>();
		if (findFor(identification) == null) {
			errors.add(Messages.getString("invalid.login"));
		}
		return errors;
	}

	private User findFor(String identification) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		    Root<User> root = criteriaQuery.from(User.class);
		    criteriaQuery.where(
		    	criteriaBuilder.or(
					criteriaBuilder.equal(root.get(User_.login), identification),
					criteriaBuilder.equal(root.get(User_.email), identification)
				)
		    );
		    return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
		    return null;
		}
	}

	@Override
	public String resetPasswordFor(String identification) {
		String password = RandomStringUtils.randomAlphanumeric(8);
		User user = findFor(identification);
		user.setPassword(DigestUtils.sha256Hex(password));
		user.setPasswordForceChange(1);
		mailService.async(Messages.getString("reset.mail.subject"), user.getEmail(),
				Messages.getString("reset.mail.body", user.getLogin(), password));
		return user.getEmail();
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
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		    Root<User> root = criteriaQuery.from(User.class);
		    criteriaQuery.where(criteriaBuilder.equal(root.get(User_.id), user.getId()));
		    return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
		    return null;
		}
	}

	@Override
	public List<String> validateBeforeCreateUser(String login,String loginConfirm,String email, String password,
			String passwordConfirm) {

		List<String> errors = new ArrayList<>();
		errors.addAll(validatePasswordPair(password, passwordConfirm));

		if (!StringUtils.equals(login, loginConfirm)) {
			errors.add(Messages.getString("admin.login.invalid.confirm"));
		}

		return errors;
	}

	@Override
	public void create(String login,String email, String password) {
		User user = new User();
		user.setPassword(DigestUtils.sha256Hex(password));
		user.setLogin(login);
		user.setPasswordForceChange(0);
		user.setEmail(email);
		super.insert(user);
	}
}
