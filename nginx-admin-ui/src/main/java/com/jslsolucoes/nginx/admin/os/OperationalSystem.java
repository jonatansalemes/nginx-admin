package com.jslsolucoes.nginx.admin.os;

import java.io.File;
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
import org.apache.commons.lang.StringUtils;

public class OperationalSystem {
	
	public static OperationalSystemInfo info(){
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		OperationalSystemInfo operationalSystemInfo = new OperationalSystemInfo(operatingSystemMXBean.getName(),
				operatingSystemMXBean.getArch(),operatingSystemMXBean.getVersion());
		if(operationalSystemInfo.getName().startsWith("Windows")){
			operationalSystemInfo.setOperationalSystemDistribution(OperationalSystemDistribution.WINDOWS);
		} else if (operationalSystemInfo.getName().startsWith("Mac")) {
			operationalSystemInfo.setOperationalSystemDistribution(OperationalSystemDistribution.MAC);
		} else if (operationalSystemInfo.getName().startsWith("Darwin")) {
			operationalSystemInfo.setOperationalSystemDistribution(OperationalSystemDistribution.DARWIN);
		} else if (operationalSystemInfo.getName().startsWith("Linux") || operationalSystemInfo.getName().startsWith("SunOS")) {
			operationalSystemInfo.setOperationalSystemDistribution(distribution());
		}
		return operationalSystemInfo;
	}

	private static OperationalSystemDistribution distribution() {
		Set<String> locations = new HashSet<String>();
		locations.add("/etc/system-release");
		locations.add("/proc/version");
		locations.add("/etc/issue");
		locations.add("/etc/lsb-release");
		locations.addAll(releases());
		String distribution = find(locations);
		if(!StringUtils.isEmpty(distribution)){
			if(distribution.contains("centos")){
				return OperationalSystemDistribution.CENTOS;
			} else {
				return OperationalSystemDistribution.UNKNOW_DISTRIBUTION;
			}
		} else {
			return OperationalSystemDistribution.UNKNOW_DISTRIBUTION;
		}
	}

	private static String find(Set<String> locations) {
		for(String location : locations){
			File file = new File(location);
			if(file.exists()){
				try {
					return FileUtils.readFileToString(file,"UTF-8").toLowerCase();
				} catch (Exception exception){
					exception.printStackTrace();
				}
			}
		}
		return null;
	}

	private static List<String> releases() {
		List<String> locations = new ArrayList<String>();
		Iterator<File> files = FileUtils.iterateFiles(new File("/etc"),new WildcardFileFilter(new String[]{"*-release","*_version"}),new IOFileFilter() {
			@Override
			public boolean accept(File file, String name) {
				return true;
			}
			
			@Override
			public boolean accept(File file) {
				return true;
			}
		});
		while(files.hasNext()){
			locations.add(files.next().getAbsolutePath());
		}
		return locations;
	}	
}
