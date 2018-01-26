package com.jslsolucoes.nginx.admin.agent.client.api;

import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxCommandLineInterfaceBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxConfigureBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxOperationalSystemInfoBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxPingBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxServerInfoBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxStatusBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.NginxUpstreamBuilder;

public class NginxAgentClientApis {

	public static Class<NginxCommandLineInterfaceBuilder> cli() {
		return NginxCommandLineInterfaceBuilder.class;
	}

	public static Class<NginxOperationalSystemInfoBuilder> os() {
		return NginxOperationalSystemInfoBuilder.class;
	}
	
	public static Class<NginxPingBuilder> ping() {
		return NginxPingBuilder.class;
	}
	
	public static Class<NginxServerInfoBuilder> info() {
		return NginxServerInfoBuilder.class;
	}
	
	public static Class<NginxStatusBuilder> status() {
		return NginxStatusBuilder.class;
	}
	
	public static Class<NginxConfigureBuilder> configure() {
		return NginxConfigureBuilder.class;
	}
	
	public static Class<NginxUpstreamBuilder> upstream() {
		return NginxUpstreamBuilder.class;
	}

}
