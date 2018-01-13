package com.jslsolucoes.nginx.admin.agent.auth;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;

import com.jslsolucoes.cdi.misc.annotation.ApplicationProperties;
import com.jslsolucoes.nginx.admin.agent.HttpHeader;


@Interceptor
@AuthHandler
@Priority(Interceptor.Priority.APPLICATION + 1)
public class AuthHandlerInterceptor {
	
	@ApplicationProperties
	private Properties properties;

	@AroundInvoke
	public Object manageTransaction(InvocationContext invocationContext) throws Exception {
		String authorizationHeader = authorizationHeaderValue(invocationContext);
		if(!StringUtils.isEmpty(authorizationHeader) && match(authorizationHeader)) {
			return invocationContext.proceed();
		} else {
			return Response.status(Status.FORBIDDEN)
					.entity("Resource forbidden")
					.build();
		}
	}
	
	public String authorizationHeaderValue(InvocationContext invocationContext) {
		List<Object> parameters = Arrays.asList(invocationContext.getParameters());
		Method method = invocationContext.getMethod();
		AtomicInteger atomicInteger = new AtomicInteger(0);
		for (Parameter parameter : method.getParameters()) {
			if (parameter.isAnnotationPresent(HeaderParam.class)) {
				HeaderParam headerParam = parameter.getAnnotation(HeaderParam.class);
				if (headerParam.value().equals(HttpHeader.AUTHORIZATION)) {
					return (String) parameters.get(atomicInteger.get());
				}
			}
			atomicInteger.getAndIncrement();
		}
		return null;
		
	}

	private boolean match(String authorizationHeader) {
		return authorizationHeader.equals(properties.getProperty("NGINX_AGENT_AUTHORIZATION_KEY"));
	}
}