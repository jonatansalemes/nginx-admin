package com.jslsolucoes.nginx.admin.agent.error;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.exception.ExceptionUtils;

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
			exception.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new NginxExceptionResponse(ExceptionUtils.getFullStackTrace(exception))).build();
		}
	}
}