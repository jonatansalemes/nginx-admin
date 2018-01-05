package com.jslsolucoes.nginx.admin.agent.runner;

public interface CommandLine {

	public String start(String bin,String conf);
	public String stop(String bin,String conf);
}
