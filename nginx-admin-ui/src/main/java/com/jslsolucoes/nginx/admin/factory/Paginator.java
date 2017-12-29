package com.jslsolucoes.nginx.admin.factory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
public class Paginator {

	private Integer resultsPerPage;
	private Integer start;
	private HttpServletRequest httpServletRequest;
	
	public Paginator() {
		
	}
	
	@Inject
	public Paginator(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}
	
	@PostConstruct
	public void calculate(){
		Integer page = parameterFor("page",1);
		Integer resultsPerPage = parameterFor("resultsPerPage",60);
		Integer start = (page * resultsPerPage) - resultsPerPage;
		
		this.resultsPerPage = resultsPerPage;
		this.start = start;
	}
	
	private Integer parameterFor(String parameterName,Integer defaultValue){
		String parameter = httpServletRequest.getParameter("resultsPerPage");
		if(parameter == null) {
			return defaultValue;
		} else {
			return Integer.valueOf(parameter);
		}
	}

	public Integer start() {
		return start;
	}

	public Integer resultsPerPage() {
		return resultsPerPage;
	}

}
