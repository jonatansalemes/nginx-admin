package com.jslsolucoes.nginx.admin.agent.resource.impl.os;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationalSystem {

	private static Logger logger = LoggerFactory.getLogger(OperationalSystem.class);

	private OperationalSystem() {

	}

	public static OperationalSystemInfo info() {
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		OperationalSystemInfo operationalSystemInfo = new OperationalSystemInfo(operatingSystemMXBean.getName(),
				operatingSystemMXBean.getArch(), operatingSystemMXBean.getVersion());
		String operationalSystem = operationalSystemInfo.getName().toLowerCase();
		if (operationalSystem.contains("windows")) {
			operationalSystemInfo.setOperationalSystemType(OperationalSystemType.WINDOWS);
		} else if (operationalSystem.contains("linux")) {
			operationalSystemInfo.setOperationalSystemType(OperationalSystemType.LINUX);
			operationalSystemInfo.setDistribution(distribution());
		} else if (operationalSystem.contains("mac") || operationalSystem.contains("darwin")) {
			operationalSystemInfo.setOperationalSystemType(OperationalSystemType.MAC);
		} else {
			operationalSystemInfo.setOperationalSystemType(OperationalSystemType.UNKNOW);
		}
		return operationalSystemInfo;
	}
	
	private static String distribution() {
		try {
			String content = FileUtils.readFileToString(new File("/etc/os-release"), "UTF-8");
			Matcher matcher = Pattern.compile("PRETTY_NAME=\"(.*?)\"").matcher(content);
			if(matcher.find()){
				return matcher.group(1);
			}
		} catch (IOException e) {
			logger.error("Could not find /etc/os-release",e);
		}
		return "unknow";
	}
}
