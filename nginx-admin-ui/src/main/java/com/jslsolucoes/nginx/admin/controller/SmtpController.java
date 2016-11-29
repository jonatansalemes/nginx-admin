package com.jslsolucoes.nginx.admin.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.mail.MailStatus;
import com.jslsolucoes.nginx.admin.model.Smtp;
import com.jslsolucoes.nginx.admin.repository.MailRepository;
import com.jslsolucoes.nginx.admin.repository.SmtpRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
@Path("smtp")
public class SmtpController {

	private Result result;
	private SmtpRepository smtpRepository;
	private MailRepository mailRepository;

	public SmtpController() {

	}

	@Inject
	public SmtpController(Result result, SmtpRepository smtpRepository, MailRepository mailRepository) {
		this.result = result;
		this.smtpRepository = smtpRepository;
		this.mailRepository = mailRepository;
	}

	public void edit() {
		this.result.include("smtp", this.smtpRepository.configuration());
	}

	public void update(Long id, String host, Integer port, Integer authenticate, Integer tls, String fromAddress,
			String username, String password) {
		this.smtpRepository.saveOrUpdate(new Smtp(id, host, port, authenticate, username, password, tls, fromAddress));
		this.result.include("updated", true);
		this.result.redirectTo(this).edit();
	}

	@Post
	public void test(String to, String subject, String message) throws InterruptedException, ExecutionException {
		Future<MailStatus> mailStatus = mailRepository.send(subject, to, message);
		this.result.include("mailStatus", mailStatus.get());
		this.result.include("sended", true);
		this.result.include("to", to);
		this.result.include("subject", subject);
		this.result.redirectTo(this).edit();
	}
}
