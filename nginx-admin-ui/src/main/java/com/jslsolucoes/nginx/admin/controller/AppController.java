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
import com.jslsolucoes.nginx.admin.annotation.Public;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
public class AppController {

	private Properties properties;
	private Result result;

	public AppController() {

	}

	@Inject
	public AppController(@Application Properties properties, Result result) {
		this.properties = properties;
		this.result = result;
	}

	@Path(value = { "/", "/home" })
	public void home() {
		this.result.include("version",properties.get("app.version"));
	}

	@Public
	@Path(value = "/version")
	public void version() {
		this.result.use(Results.json()).from(properties.get("app.version"), "version").serialize();
	}

}
