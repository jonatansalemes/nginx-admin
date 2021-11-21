package com.jslsolucoes.nginx.admin.database;

import java.nio.file.Path;

public class DatabaseSqlResource {
    private final Path path;
    private final byte[] data;
    private final Long version;

    public DatabaseSqlResource(Path path, byte[] data, Long version) {
        this.path = path;
        this.data = data;
        this.version = version;
    }

    public Path getPath() {
        return path;
    }


    public Long getVersion() {
        return version;
    }


    public byte[] getData() {
        return data;
    }
}