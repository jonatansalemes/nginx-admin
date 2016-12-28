package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import com.jslsolucoes.nginx.admin.model.AccessLog;
import com.jslsolucoes.nginx.admin.report.OriginStatistics;
import com.jslsolucoes.nginx.admin.report.UserAgentStatistics;
import com.jslsolucoes.nginx.admin.repository.ReportRepository;

@RequestScoped
public class ReportRepositoryImpl implements ReportRepository {

	private Session session;

	public ReportRepositoryImpl() {

	}

	@Inject
	public ReportRepositoryImpl(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAgentStatistics> browsers() {
		Criteria criteria = session.createCriteria(AccessLog.class);
		criteria.setProjection(Projections
			.projectionList()
			.add(Projections.count("id").as("count"))
			.add(Projections
					.groupProperty("httpUserAgent"),"userAgent")
		);
		criteria.setResultTransformer(Transformers.aliasToBean(UserAgentStatistics.class));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OriginStatistics> ips() {
		Criteria criteria = session.createCriteria(AccessLog.class);
		criteria.setProjection(Projections
			.projectionList()
			.add(Projections.count("id").as("hits"))
			.add(Projections
					.groupProperty("remoteAddress"),"ip")
		);
		criteria.setResultTransformer(Transformers.aliasToBean(OriginStatistics.class));
		return criteria.list();
	}
}
