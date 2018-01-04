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
