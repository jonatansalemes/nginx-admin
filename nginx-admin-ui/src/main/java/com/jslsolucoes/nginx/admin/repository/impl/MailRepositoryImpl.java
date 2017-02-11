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

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.model.Smtp;
import com.jslsolucoes.nginx.admin.repository.MailRepository;
import com.jslsolucoes.nginx.admin.repository.SmtpRepository;

@RequestScoped
public class MailRepositoryImpl implements MailRepository {

	private ExecutorService executorService;
	private SmtpRepository smtpRepository;
	private static Logger logger = LoggerFactory.getLogger(MailRepositoryImpl.class);

	public MailRepositoryImpl() {

	}

	@Inject
	public MailRepositoryImpl(ExecutorService executorService, SmtpRepository smtpRepository) {
		this.executorService = executorService;
		this.smtpRepository = smtpRepository;
	}

	@Override
	public Future<MailStatusType> send(String subject, String to, String message) {

		Smtp smtp = smtpRepository.configuration();
		Callable<MailStatusType> task = new Callable<MailStatusType>() {
			@Override
			public MailStatusType call() {
				try {
					Email email = new HtmlEmail();
					email.setHostName(smtp.getHost());
					email.setSmtpPort(smtp.getPort());
					email.setFrom(smtp.getFromAddress());
					if (smtp.getTls() == 1) {
						email.setStartTLSEnabled(true);
						email.setSSLOnConnect(true);
						email.setStartTLSRequired(true);
					}
					email.setCharset("ISO-8859-1");
					if (smtp.getAuthenticate() == 1) {
						email.setAuthentication(smtp.getUserName(), smtp.getPassword());
					}
					email.setSubject(subject);
					email.setMsg(message);
					email.setTo(Arrays.asList(InternetAddress.parse(to)));
					email.send();
					return MailStatusType.SENDED;
				} catch (EmailException | AddressException exception) {
					logger.error("Could not setn email", exception);
					return MailStatusType.NOT_SENDED;
				}
			}
		};
		return this.executorService.submit(task);
	}

}
