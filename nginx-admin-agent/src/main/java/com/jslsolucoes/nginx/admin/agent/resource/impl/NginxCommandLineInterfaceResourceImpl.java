package com.jslsolucoes.nginx.admin.agent.resource.impl;

import javax.enterprise.context.ApplicationScoped;

import com.jslsolucoes.runtime.RuntimeBuilder;
import com.jslsolucoes.runtime.RuntimeResult;

@ApplicationScoped
public class NginxCommandLineInterfaceResourceImpl {

	public NginxCommandLineInterfaceResourceImpl() {
		
	}
	
	public RuntimeResult kill() {
		return RuntimeBuilder.newBuilder().withCommand("sudo killall nginx").execute();
	}
	
	public RuntimeResult start(String bin, String conf) {
		return RuntimeBuilder.newBuilder().withCommand("sudo " + bin + " -c " + conf).execute();
	}
	
	public RuntimeResult stop(String bin, String conf) {
		return RuntimeBuilder.newBuilder().withCommand("sudo " + bin + " -c " + conf + " -s quit").execute();
	}
	
	public RuntimeResult version(String bin, String conf) {
		return RuntimeBuilder.newBuilder().withCommand("sudo " + bin + " -c " + conf + " -v").execute();
	}
	
	public RuntimeResult reload(String bin, String conf) {
		return RuntimeBuilder.newBuilder().withCommand("sudo " + bin + " -c " + conf + " -s reload").execute();
	}
	
	public RuntimeResult testConfig(String bin, String conf) {
		return RuntimeBuilder.newBuilder().withCommand("sudo " + bin + " -c " + conf + " -t").execute();
	}
	
	public RuntimeResult status() {
		return RuntimeBuilder.newBuilder().withCommand("sudo pgrep nginx").execute();
	}

}
