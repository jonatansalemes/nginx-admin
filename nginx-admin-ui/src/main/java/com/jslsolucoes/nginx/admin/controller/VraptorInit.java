package com.jslsolucoes.nginx.admin.controller;

import java.sql.Connection;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import br.com.caelum.vraptor.events.VRaptorInitialized;


public class VraptorInit {

	
	private Connection connection;

	@Inject 
	public VraptorInit(Connection connection) {
		this.connection = connection;
	}

	public void insert(@Observes VRaptorInitialized event) {

		
	}
}
