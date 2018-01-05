package com.jslsolucoes.nginx.admin.agent.runner.exec;

import com.jslsolucoes.runtime.RuntimeBuilder;
import com.jslsolucoes.runtime.RuntimeResult;

public class NginxCommandLineIteration {

	
	public RuntimeResult start(String bin, String conf) {
		return RuntimeBuilder.newBuilder().withCommand(bin + " -c " + conf).execute();
	}

}
