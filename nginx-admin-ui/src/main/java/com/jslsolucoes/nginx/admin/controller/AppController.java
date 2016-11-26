/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;

import com.jslsolucoes.nginx.admin.repository.AppRepository;
import com.jslsolucoes.nginx.admin.session.UserSession;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
public class AppController {

	private Result result;
	private AppRepository appRepository;
	private UserSession userSession;

	public AppController() {

	}

	@Inject
	public AppController(Result result,AppRepository appRepository,UserSession userSession) {
		this.result = result;
		this.appRepository = appRepository;
		this.userSession = userSession;
	}

	@Path(value = { "/", "/app/home" })
	public void home() {

	}

	@Path("/app/configure")
	public void configure() {

	}

	@Path("/app/configure/check")
	public void check() throws IOException {
		List<String> invalids = appRepository.checkAllRequiredConfiguration(userSession.getUser());
		if(CollectionUtils.isEmpty(invalids)){
			this.result.redirectTo(AppController.class).home();
		} else {
			this.result.include("invalids",invalids);
			this.result.redirectTo(this).configure();
		}
	}
}
