package com.jslsolucoes.nginx.admin.agent.client.api;

import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxCommandLineInterfaceBuilder;

public class NginxAgentClientApis {
	
	public static Class<NginxCommandLineInterfaceBuilder> commandLineInterface() {
		return NginxCommandLineInterfaceBuilder.class;
	}

}
