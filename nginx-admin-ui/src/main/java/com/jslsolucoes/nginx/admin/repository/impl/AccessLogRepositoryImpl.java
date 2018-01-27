package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jslsolucoes.nginx.admin.agent.NginxAgentRunner;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.access.log.NginxAccessLogCollectResponse;
import com.jslsolucoes.nginx.admin.model.AccessLog;
import com.jslsolucoes.nginx.admin.model.AccessLog_;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Nginx_;
import com.jslsolucoes.nginx.admin.repository.AccessLogRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class AccessLogRepositoryImpl extends RepositoryImpl<AccessLog> implements AccessLogRepository {

	private NginxAgentRunner nginxAgentRunner;
	private NginxRepository nginxRepository;
	private static Logger logger = LoggerFactory.getLogger(AccessLogRepositoryImpl.class);

	@Deprecated
	public AccessLogRepositoryImpl() {

	}

	@Inject
	public AccessLogRepositoryImpl(EntityManager entityManager, NginxAgentRunner nginxAgentRunner,
			NginxRepository nginxRepository) {
		super(entityManager);
		this.nginxAgentRunner = nginxAgentRunner;
		this.nginxRepository = nginxRepository;
	}

	public Long countFor(Nginx nginx) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<AccessLog> root = criteriaQuery.from(AccessLog.class);
		criteriaQuery.select(criteriaBuilder.count(root)).where(
				criteriaBuilder.equal(root.join(AccessLog_.nginx, JoinType.INNER).get(Nginx_.id), nginx.getId()));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	@Override
	public List<AccessLog> listAllFor(Nginx nginx, Integer firstResult, Integer maxResults) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AccessLog> criteriaQuery = criteriaBuilder.createQuery(AccessLog.class);
		Root<AccessLog> root = criteriaQuery.from(AccessLog.class);
		criteriaQuery.where(criteriaBuilder.equal(root.join(AccessLog_.nginx).get(Nginx_.id), nginx.getId()));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get(AccessLog_.timestamp)));
		TypedQuery<AccessLog> query = entityManager.createQuery(criteriaQuery);
		if (firstResult != null && maxResults != null) {
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}

	@Override
	public void collect() {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		for (Nginx nginx : nginxRepository.listAllConfigured()) {
			NginxResponse nginxResponse = nginxAgentRunner.collectAccessLog(nginx.getId());
			if (nginxResponse.success()) {
				NginxAccessLogCollectResponse nginxAccessLogCollectResponse = (NginxAccessLogCollectResponse) nginxResponse;
				for (FileObject fileObject : nginxAccessLogCollectResponse.getFiles()) {

					Arrays.asList(fileObject.getContent().split("\n")).stream()
							.filter(line -> line.trim().startsWith("{") && line.trim().endsWith("}")).forEach(line -> {
								try {
									AccessLog accessLog = gson.fromJson(line, AccessLog.class);
									accessLog.setNginx(nginx);
									super.insert(accessLog);
								} catch (Exception exception) {
									logger.error(line + " could'n be stored ", exception);
								}
							});
				}
			}
		}
	}

	@Override
	public void rotate() {
		for (Nginx nginx : nginxRepository.listAllConfigured()) {
			nginxAgentRunner.rotateAccessLog(nginx.getId());
		}
	}
}
