package com.jslsolucoes.nginx.admin.repository;

import java.io.IOException;
import java.util.List;

public interface ImportRepository {
	
	public List<String> validateBeforeImport(String nginxConf);

	public void importFrom(String nginxConf) throws IOException;
}
