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
import com.jslsolucoes.nginx.admin.agent.NginxAgentRunner;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.model.Configuration_;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Nginx_;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class NginxRepositoryImpl extends RepositoryImpl<Nginx> implements NginxRepository {

	private ConfigurationRepository configurationRepository;
	private NginxAgentRunner nginxAgentRunner;

	@Deprecated
	public NginxRepositoryImpl() {

	}

	@Inject
	public NginxRepositoryImpl(EntityManager entityManager,ConfigurationRepository configurationRepository,NginxAgentRunner nginxAgentRunner) {
		super(entityManager);
		this.configurationRepository = configurationRepository;
		this.nginxAgentRunner = nginxAgentRunner;
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Nginx nginx) {
		List<String> errors = new ArrayList<>();
		
		if (hasEquals(nginx) != null) {
			errors.add(Messages.getString("nginx.agent.already.exists"));
		}		
		
		NginxResponse nginxResponse = nginxAgentRunner.ping(nginx.getEndpoint(), nginx.getAuthorizationKey());
		if(nginxResponse.error()){
			errors.add(Messages.getString("nginx.agent.connection.error"));
		} else if(nginxResponse.forbidden()){
			errors.add(Messages.getString("nginx.agent.authentication.error"));
		}

		return errors;
	}
	
	@Override
	public OperationStatusType delete(Nginx nginx) {
		configurationRepository.deleteFor(nginx);
		return super.delete(nginx);
	}
	
	private Nginx hasEquals(Nginx nginx) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Nginx> criteriaQuery = criteriaBuilder.createQuery(Nginx.class);
			Root<Nginx> root = criteriaQuery.from(Nginx.class);
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(root.get(Nginx_.endpoint), nginx.getEndpoint()),
					criteriaBuilder.equal(root.get(Nginx_.name), nginx.getName())
			));
			if (nginx.getId() != null) {
				predicates.add(criteriaBuilder.notEqual(root.get(Nginx_.id), nginx.getId()));
			}
			criteriaQuery.where(predicates.toArray(new Predicate[] {}));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public List<Nginx> listAllConfigured() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Nginx> criteriaQuery = criteriaBuilder.createQuery(Nginx.class);
		Root<Nginx> root = criteriaQuery.from(Nginx.class);
		criteriaQuery.where(
				criteriaBuilder.isNotNull(root.join(Nginx_.configuration,JoinType.INNER).get(Configuration_.id))
		);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}
