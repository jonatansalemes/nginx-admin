package com.jslsolucoes.nginx.admin.controller;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import com.jslsolucoes.mail.MailStatusType;
import com.jslsolucoes.mail.config.Smtp;
import com.jslsolucoes.mail.service.MailService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
@Path("smtp")
public class SmtpController {

	private Result result;
	private MailService mailService;
	private Smtp smtp;

	@Deprecated
	public SmtpController() {
		
	}

	@Inject
	public SmtpController(Result result, MailService mailService,Smtp smtp) {
		this.result = result;
		this.mailService = mailService;
		this.smtp = smtp;
	}

	public void settings() {
		this.result.include("smtp", smtp);
	}

	@Post
	public void test(String to, String subject, String message) throws InterruptedException, ExecutionException {
		MailStatusType mailStatus = mailService.sync(subject, to, message);
		this.result.include("mailStatus", mailStatus);
		this.result.include("sended", true);
		this.result.include("to", to);
		this.result.include("subject", subject);
		this.result.redirectTo(this).settings();
	}
}
