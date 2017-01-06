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

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.jslsolucoes.nginx.admin.html.HtmlUtil;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.repository.ReportRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("report")
public class ReportController {

	private Result result;
	private ReportRepository reportRepository;
	private VirtualHostRepository virtualHostRepository;
	private HttpServletResponse httpServletResponse;

	public ReportController() {

	}

	@Inject
	public ReportController(Result result, ReportRepository reportRepository,
			VirtualHostRepository virtualHostRepository,HttpServletResponse httpServletResponse) {
		this.result = result;
		this.reportRepository = reportRepository;
		this.virtualHostRepository = virtualHostRepository;
		this.httpServletResponse = httpServletResponse;
	}

	public void validate(Long idVirtualHost, LocalDate from, LocalTime fromTime, LocalDate to, LocalTime toTime) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(
						reportRepository.validateBeforeSearch(virtualHost(idVirtualHost), from, fromTime, to, toTime)),
						"errors")
				.serialize();
	}

	public void search() {
		this.result.include("virtualHostList", virtualHostRepository.listAll());
	}
	
	@Post
	@Path("export.pdf")
	public void export(Long idVirtualHost, LocalDate from, LocalTime fromTime, LocalDate to, LocalTime toTime) throws IOException{
		httpServletResponse.setContentType("application/pdf");
		IOUtils.copy(reportRepository.statistics(virtualHost(idVirtualHost), from, fromTime, to, toTime)
				, httpServletResponse.getOutputStream());
		this.result.use(Results.status()).ok();
	}
	
	private VirtualHost virtualHost(Long id) {
		VirtualHost virtualHost = null;
		if (id != null) {
			virtualHost = new VirtualHost(id);
		}
		return virtualHost;
	}
}
