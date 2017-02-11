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

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.html.HtmlUtil;
import com.jslsolucoes.nginx.admin.repository.ImportRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import freemarker.template.TemplateException;

@Controller
@Path("import")
public class ImportController {

	private Result result;
	private ImportRepository importRepository;

	public ImportController() {
		this(null, null);
	}

	@Inject
	public ImportController(Result result, ImportRepository importRepository) {
		this.result = result;
		this.importRepository = importRepository;
	}

	public void validate(String nginxConf) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(importRepository.validateBeforeImport(nginxConf)), "errors")
				.serialize();
	}

	public void form() {

	}

	@Post
	public void execute(String nginxConf) throws IOException, TemplateException {
		importRepository.importFrom(nginxConf);
		this.result.include("imported", true);
		this.result.redirectTo(this).form();
	}

}
