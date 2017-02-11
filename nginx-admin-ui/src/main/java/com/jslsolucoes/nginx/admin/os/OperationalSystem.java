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
package com.jslsolucoes.nginx.admin.os;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationalSystem {

	private static Logger logger = LoggerFactory.getLogger(OperationalSystem.class);
	
	private OperationalSystem(){
		
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
			operationalSystemInfo.setOperationalSystemType(OperationalSystemType.NOT_IMPLEMENTED);
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
		return find(locations);
	}

	private static String find(Set<String> locations) {
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
