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
