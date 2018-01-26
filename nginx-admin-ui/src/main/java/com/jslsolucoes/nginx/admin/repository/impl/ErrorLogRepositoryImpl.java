package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.agent.NginxAgentRunner;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.error.log.NginxErrorLogCollectResponse;
import com.jslsolucoes.nginx.admin.model.ErrorLog;
import com.jslsolucoes.nginx.admin.model.ErrorLog_;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Nginx_;
import com.jslsolucoes.nginx.admin.repository.ErrorLogRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class ErrorLogRepositoryImpl extends RepositoryImpl<ErrorLog>  implements ErrorLogRepository {
	
	private NginxAgentRunner nginxAgentRunner;
	private NginxRepository nginxRepository;
	private static Logger logger = LoggerFactory.getLogger(ErrorLogRepositoryImpl.class);

	@Deprecated
	public ErrorLogRepositoryImpl() {
		
	}

	@Inject
	public ErrorLogRepositoryImpl(EntityManager entityManager,NginxAgentRunner nginxAgentRunner,NginxRepository nginxRepository) {
		super(entityManager);
		this.nginxAgentRunner = nginxAgentRunner;
		this.nginxRepository = nginxRepository;
	}
	
	public Long countFor(Nginx nginx) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<ErrorLog> root = criteriaQuery.from(ErrorLog.class);
		criteriaQuery.select(criteriaBuilder.count(root))
			.where(criteriaBuilder.equal(root.join(ErrorLog_.nginx).get(Nginx_.id), nginx.getId()));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<ErrorLog> listAllFor(Nginx nginx,Integer firstResult, Integer maxResults) {	
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ErrorLog> criteriaQuery = criteriaBuilder.createQuery(ErrorLog.class);
		Root<ErrorLog> root = criteriaQuery.from(ErrorLog.class);
		criteriaQuery.where(criteriaBuilder.equal(root.join(ErrorLog_.nginx).get(Nginx_.id), nginx.getId()));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get(ErrorLog_.timestamp)));
		TypedQuery<ErrorLog> query = entityManager.createQuery(criteriaQuery);
		if (firstResult != null && maxResults != null) {
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}
	
	@Override
	public void collect() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for(Nginx nginx : nginxRepository.listAll()) {
			NginxResponse nginxResponse = nginxAgentRunner.collectErrorLog(nginx.getId());
			if(nginxResponse.success()) {
				NginxErrorLogCollectResponse nginxErrorLogCollectResponse = (NginxErrorLogCollectResponse) nginxResponse;
				for(FileObject fileObject : nginxErrorLogCollectResponse.getFiles()) {
					try {
						for(String line : IOUtils
							.readLines(new StringReader(fileObject.getContent()))) {					
								Matcher matcher = Pattern.compile("([0-9]{4}\\/[0-9]{2}\\/[0-9]{2}\\s[0-9]{2}:[0-9]{2}:[0-9]{2})\\s\\[(.*?)\\]\\s([0-9]{1,})#([0-9]{1,}):(\\s\\*([0-9]{1,}))?\\s(.*)").matcher(line);
								if(matcher.find()){
									try {
										Date timestamp = simpleDateFormat.parse(matcher.group(1));
										String level = matcher.group(2);
										Long pid = Long.valueOf(matcher.group(3));
										Long tid = Long.valueOf(matcher.group(4));
										
										Long cid = cid(matcher.group(6));
										String message = matcher.group(7);
										
										ErrorLog errorLog = new ErrorLog();
										errorLog.setCid(cid);
										errorLog.setLevel(level);
										errorLog.setPid(pid);
										errorLog.setTid(tid);
										errorLog.setTimestamp(timestamp);
										errorLog.setMessage(message);
										errorLog.setNginx(nginx);
										super.insert(errorLog);
									} catch (ParseException exception) {
										logger.error(line + " could'n be stored ", exception);
									}
								}
						};
					} catch (IOException e) {
						logger.error("Could not read file error log lines",e);
					}
				}
			}
		}
	}

	private Long cid(String value) {
		return (!StringUtils.isEmpty(value) ?  Long.valueOf(value) : null);
	}

	@Override
	public void rotate() {
		for(Nginx nginx : nginxRepository.listAll()) {
			nginxAgentRunner.rotateErrorLog(nginx.getId());
		}
	}

}
