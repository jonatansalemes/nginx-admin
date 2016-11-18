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
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;

import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.repository.MailRepository;

@RequestScoped
public class MailRepositoryImpl implements MailRepository {

	private ConfigurationRepository configurationRepository;
	private ExecutorService executorService;

	public MailRepositoryImpl() {

	}

	@Inject
	public MailRepositoryImpl(ConfigurationRepository configurationRepository, ExecutorService executorService) {
		this.configurationRepository = configurationRepository;
		this.executorService = executorService;
	}

	@Override
	public Future<Void> send(String subject, String to, String message) {

		String hostName = configurationRepository.variable("SMTP_HOST");
		String from = configurationRepository.variable("SMTP_FROM");
		Integer port = Integer.valueOf(configurationRepository.variable("SMTP_PORT"));
		Boolean authenticate = Boolean.valueOf(configurationRepository.variable("SMTP_AUTHENTICATE"));
		Boolean tls = Boolean.valueOf(configurationRepository.variable("SMTP_TLS"));
		String userName = configurationRepository.variable("SMTP_USERNAME");
		String password = configurationRepository.variable("SMTP_PASSWORD");

		Callable<Void> task = new Callable<Void>() {
			@Override
			public Void call() {
				try {
					Email email = new HtmlEmail();
					email.setHostName(hostName);
					email.setSmtpPort(port);
					email.setFrom(from);
					if(tls){
						email.setStartTLSEnabled(true);
						email.setSSLOnConnect(true);
						email.setStartTLSRequired(true);
					}
					email.setCharset("ISO-8859-1");
					if(authenticate){
						email.setAuthentication(userName, password);
					}
					email.setSubject(subject);
					email.setMsg(message);
					email.setTo(Arrays.asList(InternetAddress.parse(to)));
					email.send();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				return null;
			}
		};
		return this.executorService.submit(task);
	}

}
