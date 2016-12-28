package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.report.OriginStatistics;
import com.jslsolucoes.nginx.admin.report.UserAgentStatistics;
import com.jslsolucoes.nginx.admin.repository.ReportRepository;
import com.jslsolucoes.tagria.lib.chart.BarDataSet;
import com.jslsolucoes.tagria.lib.chart.BarDataSetItem;
import com.jslsolucoes.tagria.lib.chart.PieDataSet;
import com.jslsolucoes.tagria.lib.chart.PieDataSetItem;

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
		this.result.include("ips", ips());
	}

	private PieDataSet browsers() {
		PieDataSet pieDataSet = new PieDataSet();
		for (UserAgentStatistics userAgentStatistics : reportRepository.browsers()) {
			pieDataSet.add(new PieDataSetItem(userAgentStatistics.getUserAgent(), userAgentStatistics.getCount()));
		}
		return pieDataSet;
	}
	
	private BarDataSet ips() {
		BarDataSet barDataSet = new BarDataSet();
		for (OriginStatistics originStatistics : reportRepository.ips()) {
			barDataSet.add(new BarDataSetItem(originStatistics.getIp(),originStatistics.getHits()));
		}
		return barDataSet;
	}

}
