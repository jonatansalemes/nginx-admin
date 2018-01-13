package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.File;
import com.jslsolucoes.i18n.Messages;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.io.FileUtils;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.model.Upstream_;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamServerRepository;
import com.jslsolucoes.template.TemplateProcessor;

@RequestScoped
public class UpstreamRepositoryImpl extends RepositoryImpl<Upstream> implements UpstreamRepository {

	private UpstreamServerRepository upstreamServerRepository;
	private NginxRepository nginxRepository;
	private ResourceIdentifierRepository resourceIdentifierRepository;

	public UpstreamRepositoryImpl() {
		// Default constructor
	}

	@Inject
	public UpstreamRepositoryImpl(EntityManager entityManager,UpstreamServerRepository upstreamServerRepository,
			NginxRepository nginxRepository, ResourceIdentifierRepository resourceIdentifierRepository) {
		super(entityManager);
		this.upstreamServerRepository = upstreamServerRepository;
		this.nginxRepository = nginxRepository;
		this.resourceIdentifierRepository = resourceIdentifierRepository;
	}

	@Override
	public OperationResult saveOrUpdate(Upstream upstream, List<UpstreamServer> upstreamServers)
			throws NginxAdminException {

		if (upstream.getId() == null) {
			upstream.setResourceIdentifier(resourceIdentifierRepository.create());
		}
		OperationResult operationResult = super.saveOrUpdate(upstream);
		upstreamServerRepository.recreate(new Upstream(operationResult.getId()), upstreamServers);
		flushAndClear();
		configure(upstream);
		return operationResult;
	}

	private void configure(Upstream upstream) throws NginxAdminException {
		try {
			Upstream upstreamToConfigure = load(upstream);
			TemplateProcessor.newBuilder().withTemplate("/template/dynamic/nginx","upstream.tpl").withData("upstream", upstreamToConfigure)
					.withOutputLocation(new File(nginxRepository.configuration().upstream(),
							upstreamToConfigure.getResourceIdentifier().getHash() + ".conf"))
					.process();
		} catch (Exception e) {
			throw new NginxAdminException(e);
		}
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
	public OperationType deleteWithResource(Upstream upstream) throws IOException {
		upstreamServerRepository.deleteAllFor(upstream);
		Upstream upstreamToDelete = load(upstream);
		String hash = upstreamToDelete.getResourceIdentifier().getHash();
		FileUtils.forceDelete(new File(nginxRepository.configuration().upstream(), hash + ".conf"));
		super.delete(upstreamToDelete);
		resourceIdentifierRepository.delete(hash);
		return OperationType.DELETE;
	}

	@Override
	public Upstream hasEquals(Upstream upstream) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Upstream> criteriaQuery = criteriaBuilder.createQuery(Upstream.class);
			Root<Upstream> root = criteriaQuery.from(Upstream.class);
			List<Predicate> predicates = new ArrayList<>();
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
	public Upstream findByName(String name) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Upstream> criteriaQuery = criteriaBuilder.createQuery(Upstream.class);
			Root<Upstream> root = criteriaQuery.from(Upstream.class);	
			criteriaQuery.where(
					criteriaBuilder.equal(root.get(Upstream_.name), name)
			);
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}
}
