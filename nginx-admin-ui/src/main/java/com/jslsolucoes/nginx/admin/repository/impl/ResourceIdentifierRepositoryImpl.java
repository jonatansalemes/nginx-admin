package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.jslsolucoes.nginx.admin.model.ResourceIdentifier;
import com.jslsolucoes.nginx.admin.model.ResourceIdentifier_;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;

@RequestScoped
public class ResourceIdentifierRepositoryImpl extends RepositoryImpl<ResourceIdentifier>
		implements ResourceIdentifierRepository {

	@Deprecated
	public ResourceIdentifierRepositoryImpl() {

	}

	@Inject
	public ResourceIdentifierRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public ResourceIdentifier create() {
		ResourceIdentifier resourceIdentifier = new ResourceIdentifier(UUID.randomUUID().toString());
		super.insert(resourceIdentifier);
		return resourceIdentifier;
	}

	@Override
	public void delete(String hash) {
		super.delete(findByHash(hash));
	}

	private ResourceIdentifier findByHash(String uuid) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<ResourceIdentifier> criteriaQuery = criteriaBuilder.createQuery(ResourceIdentifier.class);
			Root<ResourceIdentifier> root = criteriaQuery.from(ResourceIdentifier.class);
			criteriaQuery.where(criteriaBuilder.equal(root.get(ResourceIdentifier_.uuid), uuid));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

}
