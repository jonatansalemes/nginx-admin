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

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.html.HtmlUtil;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.nginx.status.NginxStatus;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("nginx")
public class NginxController {

	private Result result;
	private NginxRepository nginxRepository;
	private NginxStatus nginxStatus;

	public NginxController() {

	}

	@Inject
	public NginxController(Result result, NginxRepository nginxRepository,NginxStatus nginxStatus) {
		this.result = result;
		this.nginxRepository = nginxRepository;
		this.nginxStatus = nginxStatus;
	}

	public void validate(Long id, String bin, String settings, Integer gzip,
			Integer maxPostSize) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(nginxRepository
						.validateBeforeSaveOrUpdate(new Nginx(id, bin, settings,  gzip, maxPostSize))),
						"errors")
				.serialize();
	}

	public void edit() {
		this.result.include("nginx", this.nginxRepository.configuration());
	}

	@Post
	public void update(Long id, String bin, String settings, Integer gzip,
			Integer maxPostSize) {
		this.nginxRepository.saveOrUpdate(new Nginx(id, bin, settings,gzip, maxPostSize));
		this.result.include("updated", true);
		this.result.redirectTo(this).edit();
	}
	
	
	public void status() {
		this.result.use(Results.json()).from(nginxStatus).serialize();
	}

}
