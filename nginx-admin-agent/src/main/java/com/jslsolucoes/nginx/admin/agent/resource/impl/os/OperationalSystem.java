package com.jslsolucoes.nginx.admin.agent.resource.impl.os;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;
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
		Set<String> locations = new HashSet<>();
		locations.add("/etc/system-release");
		locations.add("/proc/version");
		locations.add("/etc/issue");
		locations.add("/etc/lsb-release");
		locations.addAll(releases());
		return clear(searchFor(locations));
	}

	private static String clear(String distribution) {
		if(!StringUtils.isEmpty(distribution)){
			return distribution.replaceAll("\n\t\r", "").trim();
		}
		return distribution;
	}

	private static String searchFor(Set<String> locations) {
		for (String location : locations) {
			File file = new File(location);
			if (file.exists()) {
				try {
					return FileUtils.readFileToString(file, "UTF-8").toLowerCase();
				} catch (IOException iOException) {
					logger.debug("Try to find another location", iOException);
				}
			}
		}
		return null;
	}

	private static List<String> releases() {
		List<String> locations = new ArrayList<>();
		Iterator<File> files = FileUtils.iterateFiles(new File("/etc"),
				new WildcardFileFilter(new String[] { "*-release", "*_version" }), new IOFileFilter() {
					@Override
					public boolean accept(File file, String name) {
						return true;
					}

					@Override
					public boolean accept(File file) {
						return true;
					}
				});
		while (files.hasNext()) {
			locations.add(files.next().getAbsolutePath());
		}
		return locations;
	}
}
