package com.jslsolucoes.nginx.admin.agent.auth;

import java.util.Properties;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

import com.jslsolucoes.cdi.misc.annotation.ApplicationProperties;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;

@Interceptor
@AuthHandler
@Priority(Interceptor.Priority.APPLICATION + 1)
public class AuthHandlerInterceptor {

	@ApplicationProperties
	@Inject
	private Properties properties;

	@Inject
	private HttpServletRequest httpServletRequest;

	@AroundInvoke
	public Object manageTransaction(InvocationContext invocationContext) throws Exception {

		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		if (!StringUtils.isEmpty(authorizationHeader) && authorizationHeader.equals(authorizationKey())) {
			return invocationContext.proceed();
		} else {
			Response response = Response.status(Status.FORBIDDEN)
					.entity(new NginxAuthenticationFailResponse("Resource forbidden")).build();
			AsyncResponse asyncResponse = asyncResponse(invocationContext);
			if(asyncResponse != null){
				asyncResponse.resume(response);
			}
			return response;
		}
	}

	private AsyncResponse asyncResponse(InvocationContext invocationContext) {
		for (Object object : invocationContext.getParameters()) {
			if (AsyncResponse.class.isInstance(object)) {
				return (AsyncResponse) object;
			}
		}
		return null;
	}

	private String authorizationKey() {
		return properties.getProperty("NGINX_AGENT_AUTHORIZATION_KEY");
	}
}