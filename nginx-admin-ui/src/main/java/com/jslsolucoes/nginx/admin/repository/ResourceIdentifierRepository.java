package com.jslsolucoes.nginx.admin.repository;

import com.jslsolucoes.nginx.admin.model.ResourceIdentifier;

public interface ResourceIdentifierRepository {

	public ResourceIdentifier create();
	
	public void delete(String hash);
}
