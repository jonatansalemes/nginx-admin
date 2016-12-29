/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.joda.time.DateTime;

import com.jslsolucoes.nginx.admin.model.AccessLog;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.report.OriginStatistics;
import com.jslsolucoes.nginx.admin.report.StatusCodeStatistics;
import com.jslsolucoes.nginx.admin.report.UserAgentStatistics;
import com.jslsolucoes.nginx.admin.repository.ReportRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostAliasRepository;

@RequestScoped
public class ReportRepositoryImpl implements ReportRepository {

	private Session session;
	private VirtualHostAliasRepository virtualHostAliasRepository;

	public ReportRepositoryImpl() {

	}

	@Inject
	public ReportRepositoryImpl(Session session,VirtualHostAliasRepository virtualHostAliasRepository) {
		this.session = session;
		this.virtualHostAliasRepository = virtualHostAliasRepository;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAgentStatistics> browsers(VirtualHost virtualHost,Date from,Date to) {
		Criteria criteria = session.createCriteria(AccessLog.class);
		criteria.setProjection(Projections
			.projectionList()
			.add(Projections.count("id").as("total"))
			.add(Projections
					.groupProperty("httpUserAgent"),"userAgent")
		);
		if(from!= null){
			criteria.add(Restrictions.ge("timestamp", new DateTime(from)
															.withTimeAtStartOfDay()
														.toDate()));
		}
		if(to!= null){
			criteria.add(Restrictions.le("timestamp", new DateTime(to)
														.hourOfDay().withMaximumValue()
														.minuteOfHour().withMaximumValue()
														.secondOfMinute().withMaximumValue()
														.millisOfSecond().withMinimumValue()
														.toDate()));
		}
		if(virtualHost != null){
			criteria.add(Restrictions.in("host", virtualHostAliasRepository
					.listAll(virtualHost)
					.stream()
					.map(virtualHostAlias -> virtualHostAlias.getAlias())
					.collect(Collectors.toSet())
					));
		}
		criteria.setResultTransformer(Transformers.aliasToBean(UserAgentStatistics.class));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OriginStatistics> ips(VirtualHost virtualHost,Date from,Date to) {
		Criteria criteria = session.createCriteria(AccessLog.class);
		criteria.setProjection(Projections
			.projectionList()
			.add(Projections.count("id").as("total"))
			.add(Projections.sum("requestLength").as("request"))
			.add(Projections.sum("bytesSent").as("response"))
			.add(Projections
					.groupProperty("remoteAddress"),"ip")
		);
		if(from!= null){
			criteria.add(Restrictions.ge("timestamp", new DateTime(from)
															.withTimeAtStartOfDay()
														.toDate()));
		}
		if(to!= null){
			criteria.add(Restrictions.le("timestamp", new DateTime(to)
														.hourOfDay().withMaximumValue()
														.minuteOfHour().withMaximumValue()
														.secondOfMinute().withMaximumValue()
														.millisOfSecond().withMinimumValue()
														.toDate()));
		}
		if(virtualHost != null){
			criteria.add(Restrictions.in("host", virtualHostAliasRepository
					.listAll(virtualHost)
					.stream()
					.map(virtualHostAlias -> virtualHostAlias.getAlias())
					.collect(Collectors.toSet())
					));
		}
		criteria.setResultTransformer(Transformers.aliasToBean(OriginStatistics.class));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StatusCodeStatistics> statuses(VirtualHost virtualHost,Date from,Date to) {
		Criteria criteria = session.createCriteria(AccessLog.class);
		criteria.setProjection(Projections
			.projectionList()
			.add(Projections.count("id").as("total"))
			.add(Projections
					.groupProperty("status"),"status")
		);
		if(from!= null){
			criteria.add(Restrictions.ge("timestamp", new DateTime(from)
															.withTimeAtStartOfDay()
														.toDate()));
		}
		if(to!= null){
			criteria.add(Restrictions.le("timestamp", new DateTime(to)
														.hourOfDay().withMaximumValue()
														.minuteOfHour().withMaximumValue()
														.secondOfMinute().withMaximumValue()
														.millisOfSecond().withMinimumValue()
														.toDate()));
		}
		if(virtualHost != null){
			criteria.add(Restrictions.in("host", virtualHostAliasRepository
					.listAll(virtualHost)
					.stream()
					.map(virtualHostAlias -> virtualHostAlias.getAlias())
					.collect(Collectors.toSet())
					));
		}
		criteria.setResultTransformer(Transformers.aliasToBean(StatusCodeStatistics.class));
		return criteria.list();
	}
}
