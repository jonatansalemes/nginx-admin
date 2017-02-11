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

import com.jslsolucoes.nginx.admin.annotation.Public;
import com.jslsolucoes.nginx.admin.repository.LogRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("task")
@Public
public class TaskController {

	private Result result;
	private LogRepository logRepository;

	public TaskController() {

	}

	@Inject
	public TaskController(Result result, LogRepository logRepository) {
		this.result = result;
		this.logRepository = logRepository;

	}

	@Path("collect/log")
	public void collectLog() {
		logRepository.collect();
		this.result.use(Results.status()).ok();
	}

	@Path("rotate/log")
	public void rotateLog() {
		logRepository.rotate();
		this.result.use(Results.status()).ok();
	}
}
