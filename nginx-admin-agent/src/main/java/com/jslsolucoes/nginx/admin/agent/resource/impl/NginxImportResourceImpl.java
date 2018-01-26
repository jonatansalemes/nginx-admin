package com.jslsolucoes.nginx.admin.agent.resource.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.jslsolucoes.nginx.admin.nginx.parser.NginxConfParser;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;

@RequestScoped
public class NginxImportResourceImpl {
	
	@Deprecated
	public NginxImportResourceImpl() {

	}
	
	public List<Directive> importFromConfiguration(String conf) {
		return NginxConfParser.newBuilder().withConfigurationFile(conf).parse();
	}

}
