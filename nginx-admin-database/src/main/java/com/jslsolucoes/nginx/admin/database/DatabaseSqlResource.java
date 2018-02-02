package com.jslsolucoes.nginx.admin.database;

import java.nio.file.Path;

public class DatabaseSqlResource {
	private Path path;
	private Long version;

	public DatabaseSqlResource(Path path, Long version) {
		this.path = path;
		this.version = version;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}