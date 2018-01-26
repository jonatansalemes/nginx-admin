package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation_;
import com.jslsolucoes.nginx.admin.model.VirtualHost_;
import com.jslsolucoes.nginx.admin.repository.VirtualHostLocationRepository;

@RequestScoped
public class VirtualHostLocationRepositoryImpl extends RepositoryImpl<VirtualHostLocation>
		implements VirtualHostLocationRepository {

	@Deprecated
	public VirtualHostLocationRepositoryImpl() {
		
	}

	@Inject
	public VirtualHostLocationRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	private List<VirtualHostLocation> listAll(VirtualHost virtualHost) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VirtualHostLocation> criteriaQuery = criteriaBuilder.createQuery(VirtualHostLocation.class);
		Root<VirtualHostLocation> root = criteriaQuery.from(VirtualHostLocation.class);
		Join<VirtualHostLocation, VirtualHost> join = root.join(VirtualHostLocation_.virtualHost, JoinType.INNER);
		criteriaQuery.where(criteriaBuilder.equal(join.get(VirtualHost_.id), virtualHost.getId()));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public void deleteAllFor(VirtualHost virtualHost) {
		for (VirtualHostLocation virtualHostLocation : listAll(virtualHost)) {
			super.delete(virtualHostLocation);
		}
		flush();
	}

	@Override
	public void recreateAllFor(VirtualHost virtualHost, List<VirtualHostLocation> aliases) {
		deleteAllFor(virtualHost);
		for (VirtualHostLocation virtualHostLocation : aliases) {
			virtualHostLocation.setVirtualHost(virtualHost);
			super.insert(virtualHostLocation);
		}
	}
}
