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
import com.jslsolucoes.nginx.admin.model.SslCertificate;
import com.jslsolucoes.nginx.admin.model.SslCertificate_;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.SslCertificateRepository;

@RequestScoped
public class SslCertificateRepositoryImpl extends RepositoryImpl<SslCertificate> implements SslCertificateRepository {

	private ResourceIdentifierRepository resourceIdentifierRepository;

	@Deprecated
	public SslCertificateRepositoryImpl() {
		
	}

	@Inject
	public SslCertificateRepositoryImpl(EntityManager entityManager, ResourceIdentifierRepository resourceIdentifierRepository) {
		super(entityManager);
		this.resourceIdentifierRepository = resourceIdentifierRepository;
	}

	@Override
	public OperationStatusType delete(SslCertificate sslCertificate) {
		SslCertificate sslCertificateToDelete = load(sslCertificate);
		String sslCertificateHash = sslCertificateToDelete.getResourceIdentifierCertificate().getUuid();
		String sslCertificatePrivateKeyHash = sslCertificateToDelete.getResourceIdentifierCertificatePrivateKey()
				.getUuid();
		super.delete(sslCertificateToDelete);
		resourceIdentifierRepository.delete(sslCertificateHash);
		resourceIdentifierRepository.delete(sslCertificatePrivateKeyHash);
		return OperationStatusType.DELETE;
	}
	
	private SslCertificate hasEquals(SslCertificate sslCertificate) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SslCertificate> criteriaQuery = criteriaBuilder.createQuery(SslCertificate.class);
			Root<SslCertificate> root = criteriaQuery.from(SslCertificate.class);
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.join(SslCertificate_.nginx, JoinType.INNER).get(Nginx_.id),
					sslCertificate.getNginx().getId()));
			predicates.add(criteriaBuilder.equal(root.get(SslCertificate_.commonName), sslCertificate.getCommonName()));
			if (sslCertificate.getId() != null) {
				predicates.add(criteriaBuilder.notEqual(root.get(SslCertificate_.id), sslCertificate.getId()));
			}
			criteriaQuery.where(predicates.toArray(new Predicate[] {}));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}
	
	@Override
	public List<String> validateBeforeSaveOrUpdate(SslCertificate sslCertificate) {
		List<String> errors = new ArrayList<>();
		
		if (hasEquals(sslCertificate) != null) {
			errors.add(Messages.getString("ssl.already.exists"));
		}

		return errors;
	}

	@Override
	public List<SslCertificate> listAllFor(Nginx nginx) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SslCertificate> criteriaQuery = criteriaBuilder.createQuery(SslCertificate.class);
		Root<SslCertificate> root = criteriaQuery.from(SslCertificate.class);
		criteriaQuery.where(criteriaBuilder.equal(root.join(SslCertificate_.nginx, JoinType.INNER).get(Nginx_.id), nginx.getId()));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(SslCertificate_.commonName)));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}
