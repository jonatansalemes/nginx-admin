package com.jslsolucoes.nginx.admin.agent.error;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;

@Interceptor
@ErrorHandler
@Priority(Interceptor.Priority.APPLICATION)
public class ErrorInterceptor {

	@AroundInvoke
	public Object manageError(InvocationContext invocationContext) throws Exception {
		try {
			return invocationContext.proceed();
		} catch (Exception exception) {
			Response response = Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new NginxExceptionResponse(exception)).build();
			AsyncResponse asyncResponse = asyncResponse(invocationContext);
			if (asyncResponse != null) {
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
}