package com.jslsolucoes.nginx.admin.agent.client.api;

import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxCommandLineInterfaceBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxOperationalSystemInfoBuilder;

public class NginxAgentClientApis {

	public static Class<NginxCommandLineInterfaceBuilder> commandLineInterface() {
		return NginxCommandLineInterfaceBuilder.class;
	}

	public static Class<NginxOperationalSystemInfoBuilder> operationalSystemInfo() {
		return NginxOperationalSystemInfoBuilder.class;
	}

}
