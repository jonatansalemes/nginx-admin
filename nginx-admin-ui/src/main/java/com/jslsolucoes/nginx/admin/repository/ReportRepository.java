package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.report.OriginStatistics;
import com.jslsolucoes.nginx.admin.report.UserAgentStatistics;

public interface ReportRepository {

	public List<UserAgentStatistics> browsers();

	public List<OriginStatistics> ips();

}
