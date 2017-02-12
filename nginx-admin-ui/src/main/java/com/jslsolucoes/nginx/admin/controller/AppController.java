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

import java.util.Properties;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.annotation.Application;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.repository.impl.ConfigurationType;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
public class AppController {

	private Properties properties;
	private Result result;
	private ConfigurationRepository configurationRepository;

	public AppController() {
		this(null, null, null);
	}

	@Inject
	public AppController(@Application Properties properties, Result result,
			ConfigurationRepository configurationRepository) {
		this.properties = properties;
		this.result = result;
		this.configurationRepository = configurationRepository;
	}

	@Path(value = { "/", "/home" })
	public void home() {
		this.result.include("version", properties.get("app.version"));
	}

	@Path("/app/edit")
	public void edit() {
		this.result.include("urlBase", configurationRepository.string(ConfigurationType.URL_BASE));
	}

	@Path("/app/update")
	@Post
	public void update(String urlBase) {
		configurationRepository.update(ConfigurationType.URL_BASE, urlBase);
		this.result.include("updated", true);
		this.result.redirectTo(this).edit();
	}

}
