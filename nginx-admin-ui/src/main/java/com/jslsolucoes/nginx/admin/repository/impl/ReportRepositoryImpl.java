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

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias;
import com.jslsolucoes.nginx.admin.repository.ReportRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostAliasRepository;
import com.jslsolucoes.vaptor4.misc.i18n.Messages;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@RequestScoped
public class ReportRepositoryImpl implements ReportRepository {

	@Resource(mappedName="java:jboss/datasources/nginx-admin")
	private DataSource dataSource;
	private VirtualHostAliasRepository virtualHostAliasRepository;

	public ReportRepositoryImpl() {
		// Default constructor
	}

	@Inject
	public ReportRepositoryImpl(VirtualHostAliasRepository virtualHostAliasRepository) {
		this.virtualHostAliasRepository = virtualHostAliasRepository;
	}

	@Override
	public List<String> validateBeforeSearch(List<VirtualHostAlias> aliases, LocalDate from, LocalTime fromTime,
			LocalDate to, LocalTime toTime) {

		List<String> errors = new ArrayList<>();
		if (new DateTime(start(from, fromTime)).isAfter(new DateTime(end(to, toTime)))) {
			errors.add(Messages.getString("report.date.interval.invalid"));
		}

		if (CollectionUtils.isEmpty(aliases)) {
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
			LocalTime toTime) throws NginxAdminException {
		try {
			Connection connection = dataSource.getConnection();
			Map<String, Object> parameters = defaultParameters();
			parameters.put("FROM", start(from, fromTime));
			parameters.put("TO", end(to, toTime));
			parameters
					.put("ALIASES",
							StringUtils
									.join(aliases.stream()
											.map(virtualHostAlias -> "'" + virtualHostAliasRepository
													.load(virtualHostAlias).getAlias() + "'")
											.collect(Collectors.toSet()), ","));
			InputStream inputStream = export("statistics", parameters, connection);
			connection.close();
			return inputStream;
		} catch (IOException | JRException | SQLException e) {
			throw new NginxAdminException(e);
		}
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
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("LOGO", ImageIO.read(getClass().getResourceAsStream("/report/image/logo.png")));
		return parameters;
	}
}
