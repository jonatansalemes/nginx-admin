package com.jslsolucoes.nginx.admin.agent.model.response;

public interface NginxResponse {

	default public boolean error() {
	      return false;
	} 
	
	default public boolean forbidden() {
	      return false;
	} 
}
