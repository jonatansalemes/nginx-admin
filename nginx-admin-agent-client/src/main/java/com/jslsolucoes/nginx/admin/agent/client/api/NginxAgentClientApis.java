package com.jslsolucoes.nginx.admin.agent.client.api;

import com.jslsolucoes.nginx.admin.agent.client.api.manager.NginxManagerBuilder;

public class NginxAgentClientApis {
	
	public static Class<NginxManagerBuilder> manager() {
		return NginxManagerBuilder.class;
	}

}
