package com.jslsolucoes.nginx.admin.nginx.detail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;

public class NginxDetailReader {

	private String ip;
	private Runner runner;
	private Nginx nginx;

	public NginxDetailReader(String ip, Runner runner, Nginx nginx) {
		this.ip = ip;
		this.runner = runner;
		this.nginx = nginx;
	}

	public NginxDetail details() {
		NginxDetail nginxDetail = new NginxDetail();
		nginxDetail.setAddress(ip);
		nginxDetail.setVersion(version());
		try {
			nginxDetail.setPid(Integer.valueOf(FileUtils.readFileToString(nginx.pid(), "UTF-8").trim()));
			nginxDetail.setUptime(uptime());
		} catch (Exception e) {
			nginxDetail.setPid(0);
			nginxDetail.setUptime(BigDecimal.ZERO);
		}

		return nginxDetail;
	}

	private String version() {
		Matcher version = Pattern.compile("([0-9]{1,}\\.[0-9]{1,}\\.[0-9]{1,})").matcher(runner.version().getOutput());
		if (version.find()) {
			return version.group(1);
		}
		return null;
	}

	private BigDecimal uptime() {
		return BigDecimal
				.valueOf(Seconds.secondsBetween(new DateTime(nginx.pid().lastModified()), new DateTime()).getSeconds())
				.divide(BigDecimal.valueOf(60), 5, RoundingMode.HALF_UP)
				.divide(BigDecimal.valueOf(60), 5, RoundingMode.HALF_UP)
				.divide(BigDecimal.valueOf(24), 5, RoundingMode.HALF_UP);
	}
}
