/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.controller;

import java.io.IOException;

import javax.inject.Inject;

import org.hibernate.HibernateException;

import com.jslsolucoes.nginx.admin.annotation.Public;
import com.jslsolucoes.nginx.admin.repository.DatabaseRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Public
@Path("database")
public class DatabaseController {

	private Result result;
	private DatabaseRepository databaseRepository;

	public DatabaseController() {

	}

	@Inject
	public DatabaseController(Result result, DatabaseRepository databaseRepository) {
		this.result = result;
		this.databaseRepository = databaseRepository;
	}

	public void installOrUpgrade() throws HibernateException, IOException {
		databaseRepository.installOrUpgrade();
		this.result.redirectTo(UserController.class).login();
	}
}
