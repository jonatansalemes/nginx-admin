package com.jslsolucoes.nginx.admin.agent.runner.exec;

import javax.enterprise.context.ApplicationScoped;

import com.jslsolucoes.runtime.RuntimeBuilder;
import com.jslsolucoes.runtime.RuntimeResult;

@ApplicationScoped
public class NginxCommandLineIteration {

	public NginxCommandLineIteration() {
		
	}
	
	public RuntimeResult start(String bin, String conf) {
		return RuntimeBuilder.newBuilder().withCommand(bin + " -c " + conf).execute();
	}

}
