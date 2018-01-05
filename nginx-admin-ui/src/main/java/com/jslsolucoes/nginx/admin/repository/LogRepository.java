package com.jslsolucoes.nginx.admin.repository;

public interface LogRepository {

	public void collect();

	public void rotate();
}