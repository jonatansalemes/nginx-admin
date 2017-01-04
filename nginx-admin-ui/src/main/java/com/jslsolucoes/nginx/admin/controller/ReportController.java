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

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.jslsolucoes.nginx.admin.html.HtmlUtil;
import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.report.OriginStatistics;
import com.jslsolucoes.nginx.admin.report.StatusCodeStatistics;
import com.jslsolucoes.nginx.admin.report.UserAgentStatistics;
import com.jslsolucoes.nginx.admin.repository.ReportRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostRepository;
import com.jslsolucoes.tagria.lib.chart.BarChartData;
import com.jslsolucoes.tagria.lib.chart.BarChartDataSet;
import com.jslsolucoes.tagria.lib.chart.PieChartData;
import com.jslsolucoes.tagria.lib.chart.PieChartDataSet;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("report")
public class ReportController {

	private Result result;
	private ReportRepository reportRepository;
	private VirtualHostRepository virtualHostRepository;

	public ReportController() {

	}

	@Inject
	public ReportController(Result result, ReportRepository reportRepository,
			VirtualHostRepository virtualHostRepository) {
		this.result = result;
		this.reportRepository = reportRepository;
		this.virtualHostRepository = virtualHostRepository;
	}

	public void validate(Long idVirtualHost, LocalDate from, LocalTime fromTime, LocalDate to, LocalTime toTime) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(
						reportRepository.validateBeforeSearch(virtualHost(idVirtualHost), from, fromTime, to, toTime)),
						"errors")
				.serialize();
	}

	private VirtualHost virtualHost(Long id) {
		VirtualHost virtualHost = null;
		if (id != null) {
			virtualHost = new VirtualHost(id);
		}
		return virtualHost;
	}

	public void dashboard(Long idVirtualHost, LocalDate from, LocalTime fromTime, LocalDate to, LocalTime toTime) {

		this.result.include("virtualHostList", virtualHostRepository.listAll());
		this.result.include("browsers", browsers(virtualHost(idVirtualHost), from, fromTime, to, toTime));
		this.result.include("statuses", status(virtualHost(idVirtualHost), from, fromTime, to, toTime));
		this.result.include("origins", origins(virtualHost(idVirtualHost), from, fromTime, to, toTime));
	}

	private PieChartData status(VirtualHost virtualHost, LocalDate from, LocalTime fromTime, LocalDate to,
			LocalTime toTime) {
		List<StatusCodeStatistics> statuses = reportRepository.statuses(virtualHost, from, fromTime, to, toTime);
		if (!CollectionUtils.isEmpty(statuses)) {
			PieChartData pieChartData = new PieChartData();
			PieChartDataSet pieChartDataSet = new PieChartDataSet();
			for (StatusCodeStatistics statusCodeStatistics : statuses) {
				pieChartDataSet.addData(statusCodeStatistics.getTotal());
				pieChartData.addLabel(String.valueOf(statusCodeStatistics.getStatus()));
			}
			pieChartData.addDataSet(pieChartDataSet);
			return pieChartData;
		}
		return null;
	}

	private PieChartData browsers(VirtualHost virtualHost, LocalDate from, LocalTime fromTime, LocalDate to,
			LocalTime toTime) {
		List<UserAgentStatistics> browsers = reportRepository.browsers(virtualHost, from, fromTime, to, toTime);
		if (!CollectionUtils.isEmpty(browsers)) {
			PieChartData pieChartData = new PieChartData();
			PieChartDataSet pieChartDataSet = new PieChartDataSet();
			for (UserAgentStatistics userAgentStatistics : browsers) {
				pieChartDataSet.addData(userAgentStatistics.getTotal());
				pieChartData.addLabel(userAgentStatistics.getUserAgent());
			}
			pieChartData.addDataSet(pieChartDataSet);
			return pieChartData;
		}
		return null;
	}

	private BarChartData origins(VirtualHost virtualHost, LocalDate from, LocalTime fromTime, LocalDate to,
			LocalTime toTime) {
		List<OriginStatistics> ips = reportRepository.ips(virtualHost, from, fromTime, to, toTime);
		if (!CollectionUtils.isEmpty(ips)) {
			BarChartData barChartData = new BarChartData();

			BarChartDataSet ip = new BarChartDataSet();
			ip.setLabel(Messages.getString("report.origin.statistics.hits"));

			BarChartDataSet request = new BarChartDataSet();
			request.setLabel(Messages.getString("report.origin.statistics.request"));

			BarChartDataSet response = new BarChartDataSet();
			response.setLabel(Messages.getString("report.origin.statistics.response"));

			for (OriginStatistics originStatistics : ips) {
				barChartData.addLabel(originStatistics.getIp());
				ip.addData(originStatistics.getTotal());
				request.addData((originStatistics.getRequest() / 1024));
				response.addData((originStatistics.getResponse() / 1024));
			}
			barChartData.addDataSet(ip);
			barChartData.addDataSet(request);
			barChartData.addDataSet(response);
			return barChartData;
		}
		return null;
	}
}
