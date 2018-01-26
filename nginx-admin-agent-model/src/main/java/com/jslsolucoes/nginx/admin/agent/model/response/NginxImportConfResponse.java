package com.jslsolucoes.nginx.admin.agent.model.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.jslsolucoes.nginx.admin.nginx.parser.directive.Directive;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.LocationDirective;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.UpstreamDirective;
import com.jslsolucoes.nginx.admin.nginx.parser.directive.VirtualHostDirective;

public class NginxImportConfResponse implements NginxResponse {

	@XmlElements({
		@XmlElement(type=VirtualHostDirective.class,name="virtualHost"),
		@XmlElement(type=LocationDirective.class,name="location"),
		@XmlElement(type=UpstreamDirective.class,name="upstream"),
	})
	private List<Directive> directives;
	
	public NginxImportConfResponse() {
		
	}
	
	public NginxImportConfResponse(List<Directive> directives) {
		this.directives = directives;	
	}

	public List<Directive> getDirectives() {
		return directives;
	}

	public void setDirectives(List<Directive> directives) {
		this.directives = directives;
	}
	
}
