package com.jslsolucoes.nginx.admin.database;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		DatabaseChecker databaseChecker = new DatabaseChecker();
		databaseChecker.check();
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}

}