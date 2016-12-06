/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.interceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.jslsolucoes.nginx.admin.annotation.Public;
import com.jslsolucoes.nginx.admin.session.UserSession;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.view.Results;

@RequestScoped
@Intercepts
public class AuthenticationInterceptor {

	@Inject
	private UserSession userSession;

	@Inject
	private Result result;

	@Accepts
	public boolean accepts(ControllerMethod controllerMethod) {
		return !(controllerMethod.getController().getType().isAnnotationPresent(Public.class)
				|| controllerMethod.containsAnnotation(Public.class));
	}

	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		if (userSession.getUser() != null) {
			stack.next();
		} else {
			this.result.use(Results.http()).sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

}