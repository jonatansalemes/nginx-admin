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

import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.model.UpstreamServer_;
import com.jslsolucoes.nginx.admin.model.Upstream_;
import com.jslsolucoes.nginx.admin.repository.UpstreamServerRepository;

@RequestScoped
public class UpstreamServerRepositoryImpl extends RepositoryImpl<UpstreamServer> implements UpstreamServerRepository {

	@Deprecated
	public UpstreamServerRepositoryImpl() {
		
	}

	@Inject
	public UpstreamServerRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	private List<UpstreamServer> listAll(Upstream upstream) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UpstreamServer> criteriaQuery = criteriaBuilder.createQuery(UpstreamServer.class);
		Root<UpstreamServer> root = criteriaQuery.from(UpstreamServer.class);
		Join<UpstreamServer, Upstream> join = root.join(UpstreamServer_.upstream, JoinType.INNER);
		criteriaQuery.where(criteriaBuilder.equal(join.get(Upstream_.id), upstream.getId()));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public void deleteAllFor(Upstream upstream) {
		for (UpstreamServer upstreamServer : listAll(upstream)) {
			super.delete(upstreamServer);
		}
		flush();
	}

	@Override
	public void create(Upstream upstream, List<UpstreamServer> upstreamServers) {
		deleteAllFor(upstream);
		for (UpstreamServer upstreamServer : upstreamServers) {
			upstreamServer.setUpstream(upstream);
			super.insert(upstreamServer);
		}
	}

}
