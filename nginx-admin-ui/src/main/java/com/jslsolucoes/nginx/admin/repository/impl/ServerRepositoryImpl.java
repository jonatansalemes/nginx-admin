package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.jslsolucoes.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Nginx_;
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.model.Server_;
import com.jslsolucoes.nginx.admin.repository.ServerRepository;

@RequestScoped
public class ServerRepositoryImpl extends RepositoryImpl<Server> implements ServerRepository {

	@Deprecated
	public ServerRepositoryImpl() {
		
	}

	@Inject
	public ServerRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Server server) {
		List<String> errors = new ArrayList<>();

		if (hasEquals(server) != null) {
			errors.add(Messages.getString("server.already.exists"));
		}

		return errors;
	}

	@Override
	public Server hasEquals(Server server) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Server> criteriaQuery = criteriaBuilder.createQuery(Server.class);
			Root<Server> root = criteriaQuery.from(Server.class);
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.join(Server_.nginx, JoinType.INNER).get(Nginx_.id),
					server.getNginx().getId()));
			predicates.add(criteriaBuilder.equal(root.get(Server_.ip), server.getIp()));
			if (server.getId() != null) {
				predicates.add(criteriaBuilder.notEqual(root.get(Server_.id), server.getId()));
			}
			criteriaQuery.where(predicates.toArray(new Predicate[] {}));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public Server findByIp(String ip) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Server> criteriaQuery = criteriaBuilder.createQuery(Server.class);
			Root<Server> root = criteriaQuery.from(Server.class);
			criteriaQuery.where(criteriaBuilder.equal(root.get(Server_.ip), ip));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public List<Server> listAllFor(Nginx nginx) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Server> criteriaQuery = criteriaBuilder.createQuery(Server.class);
		Root<Server> root = criteriaQuery.from(Server.class);
		criteriaQuery
				.where(criteriaBuilder.equal(root.join(Server_.nginx, JoinType.INNER).get(Nginx_.id), nginx.getId()));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Server_.ip)));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}
