package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.Strategy;
import com.jslsolucoes.nginx.admin.repository.StrategyRepository;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Controller
@Path("strategy")
public class StrategyController {

	private Result result;
	private StrategyRepository strategyRepository;

	@Deprecated
	public StrategyController() {

	}

	@Inject
	public StrategyController(Result result, StrategyRepository strategyRepository) {
		this.result = result;
		this.strategyRepository = strategyRepository;
	}

	public void data(Long id) {
		this.result.include("strategy", strategyRepository.load(new Strategy(id)));
	}

	
}
