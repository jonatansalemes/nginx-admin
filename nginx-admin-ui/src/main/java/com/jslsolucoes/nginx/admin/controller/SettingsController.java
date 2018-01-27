package com.jslsolucoes.nginx.admin.controller;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import com.jslsolucoes.mail.MailStatusType;
import com.jslsolucoes.mail.service.MailService;
import com.jslsolucoes.nginx.admin.ui.config.Configuration;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
@Path("settings")
public class SettingsController {

	private Result result;
	private MailService mailService;
	private Configuration configuration;

	@Deprecated
	public SettingsController() {

	}

	@Inject
	public SettingsController(Result result, MailService mailService, Configuration configuration) {
		this.result = result;
		this.mailService = mailService;
		this.configuration = configuration;
	}

	public void home() {
		// home logic
	}

	public void smtp() {
		this.result.include("smtp", configuration.getSmtp());
	}

	public void app() {
		this.result.include("application", configuration.getApplication());
	}

	@Post
	@Path("smtp/test")
	public void smtpTest(String to, String subject, String message) throws InterruptedException, ExecutionException {
		MailStatusType mailStatus = mailService.sync(subject, to, message);
		this.result.include("mailStatus", mailStatus);
		this.result.include("sended", true);
		this.result.include("to", to);
		this.result.include("subject", subject);
		this.result.redirectTo(this).smtp();
	}
}
