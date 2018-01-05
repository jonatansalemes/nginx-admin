package com.jslsolucoes.nginx.admin.agent.runner;

public class LinuxRunner implements CommandLine {

	@Override
	public String start(String bin,String conf) {
		return bin + " -c " + conf;
	}

	@Override
	public String stop(String bin,String conf) {
		return bin + " -c " + conf + " -s quit";
	}

}
