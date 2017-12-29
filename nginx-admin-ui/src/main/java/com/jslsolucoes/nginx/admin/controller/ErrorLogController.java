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

import com.jslsolucoes.nginx.admin.repository.ErrorLogRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("errorLog")
public class ErrorLogController {

	private Result result;
	private ErrorLogRepository errorLogRepository;

	public ErrorLogController() {
		this(null, null);
	}

	@Inject
	public ErrorLogController(Result result, ErrorLogRepository errorLogRepository) {
		this.result = result;
		this.errorLogRepository = errorLogRepository;
	}

	public void list() {
		this.result.include("errorLogContent",errorLogRepository.content());
	}

}
