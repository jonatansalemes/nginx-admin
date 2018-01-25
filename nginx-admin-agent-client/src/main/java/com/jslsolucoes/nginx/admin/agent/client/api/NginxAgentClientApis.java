package com.jslsolucoes.nginx.admin.agent.client.api;

import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxCommandLineInterfaceBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxConfigureBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxOperationalSystemInfoBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxPingBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxServerInfoBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxUpstreamBuilder;

public class NginxAgentClientApis {

	public static Class<NginxCommandLineInterfaceBuilder> commandLineInterface() {
		return NginxCommandLineInterfaceBuilder.class;
	}

	public static Class<NginxOperationalSystemInfoBuilder> operationalSystemInfo() {
		return NginxOperationalSystemInfoBuilder.class;
	}
	
	public static Class<NginxPingBuilder> ping() {
		return NginxPingBuilder.class;
	}
	
	public static Class<NginxServerInfoBuilder> nginxServerInfo() {
		return NginxServerInfoBuilder.class;
	}
	
	public static Class<NginxConfigureBuilder> configure() {
		return NginxConfigureBuilder.class;
	}
	
	public static Class<NginxUpstreamBuilder> upstream() {
		return NginxUpstreamBuilder.class;
	}

}
