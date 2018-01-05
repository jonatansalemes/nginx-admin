package com.jslsolucoes.nginx.admin.agent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.jslsolucoes.nginx.admin.agent.resource.IndexResource;

@ApplicationPath("/")
public class JaxRsApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(Arrays.asList(IndexResource.class));
	}

}
