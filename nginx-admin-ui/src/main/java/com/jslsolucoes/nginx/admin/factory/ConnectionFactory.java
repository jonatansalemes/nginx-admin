package com.jslsolucoes.nginx.admin.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

public class ConnectionFactory {

	@Resource(lookup="java:jboss/datasources/AdminDS")
	private DataSource dataSource;
	
	@Produces
	@RequestScoped
	public Connection getInstance() throws SQLException{
		return dataSource.getConnection();
	}
	
	public void close(@Disposes Connection connection) throws SQLException{
		if(!connection.isClosed()){
			connection.close();
		}
	}
	
	
}
