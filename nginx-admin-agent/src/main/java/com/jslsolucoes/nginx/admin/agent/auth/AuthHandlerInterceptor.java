package com.jslsolucoes.nginx.admin.agent.auth;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.jslsolucoes.nginx.admin.agent.HttpHeader;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;


@Interceptor
@AuthHandler
@Priority(Interceptor.Priority.APPLICATION + 1)
public class AuthHandlerInterceptor {

	@AroundInvoke
	public Object manageTransaction(InvocationContext invocationContext) throws Exception {
		List<Object> parameters = Arrays.asList(invocationContext.getParameters());
		Method method = invocationContext.getMethod();
		AtomicInteger atomicInteger = new AtomicInteger(0);
		for (Parameter parameter : method.getParameters()) {
			if (parameter.isAnnotationPresent(HeaderParam.class)) {
				HeaderParam headerParam = parameter.getAnnotation(HeaderParam.class);
				if (headerParam.value().equals(HttpHeader.AUTHORIZATION)
						&& match((String) parameters.get(atomicInteger.get()))) {
					return Response.status(Status.FORBIDDEN)
							.entity(new NginxExceptionResponse(ExceptionUtils.getFullStackTrace(new Exception("Resource forbidden"))))
							.build();
				}
			}
			atomicInteger.getAndIncrement();
		}
		return invocationContext.proceed();
	}

	private boolean match(String apiKey) {
		return true;
	}
}