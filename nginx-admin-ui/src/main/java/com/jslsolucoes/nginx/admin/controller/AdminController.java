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

import com.jslsolucoes.nginx.admin.nginx.Runner;
import com.jslsolucoes.nginx.admin.os.OperationalSystem;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("admin")
public class AdminController {

	private Result result;
	private Runner runner;

	public AdminController() {

	}

	@Inject
	public AdminController(Result result, Runner runner, NginxRepository nginxRepository) {
		this.result = result;
		this.runner = runner.configure(nginxRepository.configuration());
	}

	public void dashboard() {
		this.result.include("so", OperationalSystem.info());
	}

	public void configure() {

	}

	public void stop() {

		this.result.include("runtimeResult", runner.stop());
		this.result.redirectTo(this).dashboard();
	}

	public void start() {
		this.result.include("runtimeResult", runner.start());
		this.result.redirectTo(this).dashboard();
	}

	public void status() {
		this.result.include("runtimeResult", runner.status());
		this.result.redirectTo(this).dashboard();
	}

	public void restart() {
		this.result.include("runtimeResult", runner.restart());
		this.result.redirectTo(this).dashboard();
	}

	public void testConfig() {
		this.result.include("runtimeResult", runner.testConfig());
		this.result.redirectTo(this).dashboard();
	}

}
