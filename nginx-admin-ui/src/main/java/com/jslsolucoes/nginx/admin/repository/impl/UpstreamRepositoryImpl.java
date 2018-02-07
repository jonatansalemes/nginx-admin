package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.model.Upstream_;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamServerRepository;

@RequestScoped
public class UpstreamRepositoryImpl extends RepositoryImpl<Upstream> implements UpstreamRepository {

	private UpstreamServerRepository upstreamServerRepository;
	private ResourceIdentifierRepository resourceIdentifierRepository;

	@Deprecated
	public UpstreamRepositoryImpl() {

	}

	@Inject
	public UpstreamRepositoryImpl(EntityManager entityManager, UpstreamServerRepository upstreamServerRepository,
			NginxRepository nginxRepository, ResourceIdentifierRepository resourceIdentifierRepository) {
		super(entityManager);
		this.upstreamServerRepository = upstreamServerRepository;
		this.resourceIdentifierRepository = resourceIdentifierRepository;
	}

	@Override
	public OperationResult saveOrUpdate(Upstream upstream, List<UpstreamServer> upstreamServers) {
		OperationResult operationResult = super.saveOrUpdate(upstream);
		upstreamServerRepository.create(new Upstream(operationResult.getId()), upstreamServers);
		return operationResult;
	}

	@Override
	public List<Upstream> listAllFor(Nginx nginx) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Upstream> criteriaQuery = criteriaBuilder.createQuery(Upstream.class);
		Root<Upstream> root = criteriaQuery.from(Upstream.class);
		criteriaQuery
				.where(criteriaBuilder.equal(root.join(Upstream_.nginx, JoinType.INNER).get(Nginx_.id), nginx.getId()));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Upstream_.name)));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Upstream upstream, List<UpstreamServer> upstreamServers) {
		List<String> errors = new ArrayList<>();

		if (upstreamServers.stream()
				.map(upstreamServer -> upstreamServer.getServer().getId() + ":" + upstreamServer.getPort())
				.collect(Collectors.toSet()).size() != upstreamServers.size()) {
			errors.add(Messages.getString("upstream.servers.mapped.twice"));
		}

		if (hasEquals(upstream) != null) {
			errors.add(Messages.getString("upstream.already.exists"));
		}

		return errors;
	}

	@Override
	public OperationStatusType delete(Upstream upstream) {
		Upstream upstreamToDelete = load(upstream);
		upstreamServerRepository.deleteAllFor(upstreamToDelete);
		String uuid = upstreamToDelete.getResourceIdentifier().getUuid();
		super.delete(upstreamToDelete);
		resourceIdentifierRepository.delete(uuid);
		return OperationStatusType.DELETE;
	}

	private Upstream hasEquals(Upstream upstream) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Upstream> criteriaQuery = criteriaBuilder.createQuery(Upstream.class);
			Root<Upstream> root = criteriaQuery.from(Upstream.class);
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.join(Upstream_.nginx, JoinType.INNER).get(Nginx_.id),
					upstream.getNginx().getId()));
			predicates.add(criteriaBuilder.equal(root.get(Upstream_.name), upstream.getName()));
			if (upstream.getId() != null) {
				predicates.add(criteriaBuilder.notEqual(root.get(Upstream_.id), upstream.getId()));
			}
			criteriaQuery.where(predicates.toArray(new Predicate[] {}));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public Upstream searchFor(String name, Nginx nginx) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Upstream> criteriaQuery = criteriaBuilder.createQuery(Upstream.class);
			Root<Upstream> root = criteriaQuery.from(Upstream.class);
			criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(Upstream_.name), name),
					criteriaBuilder.equal(root.join(Upstream_.nginx, JoinType.INNER).get(Nginx_.id), nginx.getId())));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}
}
