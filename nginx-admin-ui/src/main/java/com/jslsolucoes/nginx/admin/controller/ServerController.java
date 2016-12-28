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
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.repository.ServerRepository;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("server")
public class ServerController {

	private Result result;
	private ServerRepository serverRepository;

	public ServerController() {

	}

	@Inject
	public ServerController(Result result, ServerRepository serverRepository) {
		this.result = result;
		this.serverRepository = serverRepository;
	}

	public void list() {
		this.result.include("serverList", serverRepository.listAll());
	}

	public void form() {

	}

	public void validate(Long id, String ip) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(serverRepository.validateBeforeSaveOrUpdate(new Server(id, ip))),
						"errors")
				.serialize();
	}

	@Path("edit/{id}")
	public void edit(Long id) {
		this.result.include("server", serverRepository.load(new Server(id)));
		this.result.forwardTo(this).form();
	}

	@Path("delete/{id}")
	public void delete(Long id) throws Exception {
		this.result.include("operation", serverRepository.delete(new Server(id)));
		this.result.redirectTo(this).list();
	}

	@Post
	public void saveOrUpdate(Long id, String ip) throws Exception {
		OperationResult operationResult = serverRepository.saveOrUpdate(new Server(id, ip));
		this.result.include("operation", operationResult.getOperationType());
		this.result.redirectTo(this).edit(operationResult.getId());
	}
}
