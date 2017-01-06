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
package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.common.collect.Lists;
import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.repository.ReportRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.type.PdfVersionEnum;
import net.sf.jasperreports.export.type.PdfaConformanceEnum;

@RequestScoped
public class ReportRepositoryImpl implements ReportRepository {

	private Session session;

	public ReportRepositoryImpl() {

	}

	@Inject
	public ReportRepositoryImpl(Session session) {
		this.session = session;
	}

	@Override
	public List<String> validateBeforeSearch(VirtualHost virtualHost, LocalDate from, LocalTime fromTime, LocalDate to,
			LocalTime toTime) {

		List<String> errors = new ArrayList<String>();
		if (from != null && to != null && new DateTime(start(from, fromTime)).isAfter(new DateTime(end(to, toTime)))) {
			errors.add(Messages.getString("report.date.interval.invalid"));
		}
		return errors;

	}

	public Date start(LocalDate localDate, LocalTime localTime) {
		if (localTime == null) {
			return localDate.toDateTimeAtStartOfDay().toDate();
		} else {
			return localDate.toDateTime(localTime).toDate();
		}
	}

	public Date end(LocalDate localDate, LocalTime localTime) {
		if (localTime == null) {
			return localDate.toDateTimeAtCurrentTime().hourOfDay().withMaximumValue().minuteOfHour().withMaximumValue()
					.secondOfMinute().withMaximumValue().millisOfSecond().withMinimumValue().toDate();
		} else {
			return localDate.toDateTime(localTime).toDate();
		}
	}

	@Override
	public InputStream statistics(VirtualHost virtualHost, LocalDate from, LocalTime fromTime, LocalDate to,
			LocalTime toTime) {
		return session.doReturningWork(new ReturningWork<InputStream>() {
			@Override
			public InputStream execute(Connection connection) throws SQLException {
				try {
					Map<String, Object> parameters = defaultParameters();

					if (from != null) {
						parameters.put("FROM", start(from, fromTime));
					}

					if (to != null) {
						parameters.put("TO", end(to, toTime));
					}

					if (virtualHost != null) {
						parameters.put("ID_VIRTUAL_HOST", virtualHost.getId());
					}

					return export("statistics", parameters, connection);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		});
	}

	/*
	 select count(id) as hits from admin.access_log;
	select count(id) as hits,remote_addr as ip,((cast(count(id) as decimal) *cast(100 as decimal)) / (select cast(count(id) as decimal) from admin.access_log)) as "%" from admin.access_log group by remote_addr order by hits desc;
	select count(id) as hits,http_referrer as url,((cast(count(id) as decimal) *cast(100 as decimal)) / (select cast(count(id) as decimal) from admin.access_log)) as "%" from admin.access_log group by http_referrer order by hits desc;
	select count(id) as hits,request_uri as url,server_name as host,((cast(count(id) as decimal) *cast(100 as decimal)) / (select cast(count(id) as decimal) from admin.access_log)) as "%" from admin.access_log group by request_uri,server_name order by hits desc;
	select count(id) as hits,status as code,((cast(count(id) as decimal) *cast(100 as decimal)) / (select cast(count(id) as decimal) from admin.access_log)) as "%" from admin.access_log group by status order by hits desc;
	select count(id) as hits,http_user_agent as browser,((cast(count(id) as decimal) *cast(100 as decimal)) / (select cast(count(id) as decimal) from admin.access_log)) as "%" from admin.access_log group by http_user_agent order by hits desc;
	 */

	private InputStream export(String jasper, Map<String, Object> parameters, Connection connection)
			throws JRException {
		JRPdfExporter jrPdfExporter = new JRPdfExporter();
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		configuration.setPdfaConformance(PdfaConformanceEnum.PDFA_1A);
		configuration.setIccProfilePath("sRGB_v4_ICC_preference.icc");
		configuration.setPdfVersion(PdfVersionEnum.VERSION_1_7);
		configuration.setTagged(true);
		configuration.setTagLanguage("pt-BR");

		jrPdfExporter.setConfiguration(configuration);
		jrPdfExporter.setExporterInput(SimpleExporterInput.getInstance(Lists.newArrayList(JasperFillManager
				.fillReport(getClass().getResourceAsStream("/report/" + jasper + ".jasper"), parameters, connection))));
		java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
		jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		jrPdfExporter.exportReport();
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	private Map<String, Object> defaultParameters() throws IOException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("LOGO", ImageIO.read(getClass().getResourceAsStream("/report/image/logo.png")));
		return parameters;
	}
}
