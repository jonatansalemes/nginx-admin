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

import com.jslsolucoes.nginx.admin.repository.AccessLogRepository;
import com.jslsolucoes.vaptor4.misc.pagination.Paginator;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("accessLog")
public class AccessLogController {

	private Result result;
	private AccessLogRepository accessLogRepository;
	private Paginator paginator;

	public AccessLogController() {
		this(null, null,null);
	}

	@Inject
	public AccessLogController(Result result, AccessLogRepository accessLogRepository,Paginator paginator) {
		this.result = result;
		this.accessLogRepository = accessLogRepository;
		this.paginator = paginator;
	}

	
	public void list() {
		this.result.include("totalResults",accessLogRepository.count());
		this.result.include("accessLogList", accessLogRepository.listAll(paginator.start(), paginator.resultsPerPage()));
	}

}
