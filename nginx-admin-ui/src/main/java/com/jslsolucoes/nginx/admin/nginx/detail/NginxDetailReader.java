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
package com.jslsolucoes.nginx.admin.nginx.detail;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;

public class NginxDetailReader {

	private Runner runner;
	private Nginx nginx;
	private static Logger logger = LoggerFactory.getLogger(NginxDetailReader.class);

	public NginxDetailReader(Runner runner, Nginx nginx) {
		this.runner = runner;
		this.nginx = nginx;
	}

	public NginxDetail details() {
		NginxDetail nginxDetail = new NginxDetail();
		nginxDetail.setAddress(address());
		nginxDetail.setVersion(version());
		try {
			nginxDetail.setPid(Integer.valueOf(FileUtils.readFileToString(nginx.pid(), "UTF-8").trim()));
			nginxDetail.setUptime(uptime());
		} catch (IOException ioException) {
			logger.error("Could not read pid file", ioException);
			nginxDetail.setPid(0);
			nginxDetail.setUptime(BigDecimal.ZERO);
		}
		return nginxDetail;
	}

	private String address() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			Set<String> everything = new HashSet<String>();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				if (networkInterface.isUp() && !networkInterface.isLoopback()) {
					everything.addAll(Collections.list(networkInterface.getInetAddresses()).stream()
							.filter(inetAddress -> inetAddress instanceof Inet4Address).map(InetAddress::getHostAddress)
							.collect(Collectors.toSet()));
				}
			}
			return StringUtils.join(everything, ",");
		} catch (SocketException socketException) {
			logger.error("Could not read network interfaces", socketException);
			return "(" + Messages.getString("nginx.network.card.not.found") + ")";
		}
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
