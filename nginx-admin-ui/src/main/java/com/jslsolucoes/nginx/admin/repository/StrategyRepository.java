package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.Strategy;

public interface StrategyRepository {

	public List<Strategy> listAll();

	public Strategy findByName(String name);

}
