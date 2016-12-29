package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.report.OriginStatistics;
import com.jslsolucoes.nginx.admin.report.StatusCodeStatistics;
import com.jslsolucoes.nginx.admin.report.UserAgentStatistics;
import com.jslsolucoes.nginx.admin.repository.ReportRepository;
import com.jslsolucoes.tagria.lib.chart.BarChartData;
import com.jslsolucoes.tagria.lib.chart.BarChartDataSet;
import com.jslsolucoes.tagria.lib.chart.PieChartData;
import com.jslsolucoes.tagria.lib.chart.PieChartDataSet;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("report")
public class ReportController {

	private Result result;
	private ReportRepository reportRepository;

	public ReportController() {

	}

	@Inject
	public ReportController(Result result, ReportRepository reportRepository) {
		this.result = result;
		this.reportRepository = reportRepository;
	}

	public void dashboard() {
		this.result.include("browsers", browsers());
		this.result.include("statuses", status());
		this.result.include("origins", origins());
	}

	private PieChartData status() {
		PieChartData pieChartData = new PieChartData();
		PieChartDataSet pieChartDataSet = new PieChartDataSet();
		for (StatusCodeStatistics statusCodeStatistics : reportRepository.statuses()) {
			pieChartDataSet.addData(statusCodeStatistics.getTotal());	
			pieChartData.addLabel(String.valueOf(statusCodeStatistics.getStatus()));
		}
		pieChartData.addDataSet(pieChartDataSet);
		return pieChartData;
	}

	private PieChartData browsers() {
		PieChartData pieChartData = new PieChartData();
		PieChartDataSet pieChartDataSet = new PieChartDataSet();
		for (UserAgentStatistics userAgentStatistics : reportRepository.browsers()) {
			pieChartDataSet.addData(userAgentStatistics.getTotal());	
			pieChartData.addLabel(userAgentStatistics.getUserAgent());
		}
		pieChartData.addDataSet(pieChartDataSet);
		return pieChartData;
	}

	private BarChartData origins() {
		BarChartData barChartData = new BarChartData();

		BarChartDataSet ip = new BarChartDataSet();
		ip.setLabel("Hits");

		BarChartDataSet request = new BarChartDataSet();
		request.setLabel("Request total (Kbytes)");

		BarChartDataSet response = new BarChartDataSet();
		response.setLabel("Response total (Kbytes)");

		for (OriginStatistics originStatistics : reportRepository.ips()) {
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

}
