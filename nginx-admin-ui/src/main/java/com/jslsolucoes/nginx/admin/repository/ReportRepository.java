package com.jslsolucoes.nginx.admin.repository;

import java.io.InputStream;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias;

public interface ReportRepository {

	public List<String> validateBeforeSearch(List<VirtualHostAlias> aliases, LocalDate from, LocalTime fromTime,
			LocalDate to, LocalTime toTime,Nginx nginx);

	public InputStream statistics(List<VirtualHostAlias> aliases, LocalDate from, LocalTime fromTime, LocalDate to,
			LocalTime toTime,Nginx nginx) throws NginxAdminException;

}
