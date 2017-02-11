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
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias;
import com.jslsolucoes.nginx.admin.repository.ReportRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostAliasRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@RequestScoped
public class ReportRepositoryImpl implements ReportRepository {

	private Session session;
	private VirtualHostAliasRepository virtualHostAliasRepository;
	private static Logger logger = LoggerFactory.getLogger(ReportRepository.class);

	public ReportRepositoryImpl() {

	}

	@Inject
	public ReportRepositoryImpl(Session session,VirtualHostAliasRepository virtualHostAliasRepository) {
		this.session = session;
		this.virtualHostAliasRepository = virtualHostAliasRepository;
	}

	@Override
	public List<String> validateBeforeSearch(List<VirtualHostAlias> aliases, LocalDate from, LocalTime fromTime, LocalDate to,
			LocalTime toTime) {

		List<String> errors = new ArrayList<String>();
		if (new DateTime(start(from, fromTime)).isAfter(new DateTime(end(to, toTime)))) {
			errors.add(Messages.getString("report.date.interval.invalid"));
		}
		
		if(CollectionUtils.isEmpty(aliases)){
			errors.add(Messages.getString("report.aliases.empty"));
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
	public InputStream statistics(List<VirtualHostAlias> aliases, LocalDate from, LocalTime fromTime, LocalDate to,
			LocalTime toTime) {
		return session.doReturningWork(new ReturningWork<InputStream>() {
			@Override
			public InputStream execute(Connection connection) throws SQLException {
				try {
					Map<String, Object> parameters = defaultParameters();
					parameters.put("FROM", start(from, fromTime));
					parameters.put("TO", end(to, toTime));
					parameters.put("ALIASES", StringUtils.join(aliases
										.stream()
										.map(virtualHostAlias ->{
											return "'" + virtualHostAliasRepository.load(virtualHostAlias).getAlias() + "'";
										}).collect(Collectors.toSet())
										,",") );
					return export("statistics", parameters, connection);
				} catch (JRException|IOException exception) {
					logger.error("Could not render report", exception);
					return null;
				}
			}
		});
	}

	private InputStream export(String jasper, Map<String, Object> parameters, Connection connection)
			throws JRException {
		JRPdfExporter jrPdfExporter = new JRPdfExporter();
		
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
