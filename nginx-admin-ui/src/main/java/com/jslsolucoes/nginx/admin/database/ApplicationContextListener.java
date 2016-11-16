package com.jslsolucoes.nginx.admin.database;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;


@WebListener
public class ApplicationContextListener implements ServletContextListener {
	
	@Resource(lookup="java:jboss/datasources/AdminDS")
	private DataSource dataSource;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		DatabaseChecker databaseChecker = new DatabaseChecker(dataSource);
		databaseChecker.check();
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}

}