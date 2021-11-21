package com.jslsolucoes.nginx.admin.database;

import com.jslsolucoes.nginx.admin.database.model.DatabaseHistory;
import com.jslsolucoes.nginx.admin.database.repository.DatabaseHistoryRepository;
import com.jslsolucoes.nginx.admin.database.repository.impl.DatabaseHistoryRepositoryImpl;
import com.jslsolucoes.nginx.admin.database.repository.impl.driver.DriverQuery;
import com.jslsolucoes.nginx.admin.database.repository.impl.driver.H2DriverQuery;
import com.jslsolucoes.nginx.admin.database.repository.impl.driver.MariaDbDriverQuery;
import com.jslsolucoes.nginx.admin.database.repository.impl.driver.MysqlDriverQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DatabaseMigrationBuilder {

    private String host = "localhost";
    private Integer port;
    private String database = "public";
    private String username;
    private String password;
    private String location = "~";
    private DatabaseDriver databaseDriver;
    private String table = "db_migrate_history";
    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrationBuilder.class);
    private List<String> classpaths;
    private Properties properties = new Properties();

    static {
        try {
            DriverManager.registerDriver(new org.h2.Driver());
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DatabaseMigrationBuilder() {

    }

    public static DatabaseMigrationBuilder newBuilder() {
        return new DatabaseMigrationBuilder();
    }

    public DatabaseMigrationBuilder withLocation(String location) {
        this.location = location;
        return this;
    }

    public DatabaseMigrationBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public DatabaseMigrationPropertiesBuilder withProperties() {
        return DatabaseMigrationPropertiesBuilder.newBuilder(this, properties);
    }

    public DatabaseMigrationBuilder withProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public DatabaseMigrationBuilder withDatabase(String database) {
        this.database = database;
        return this;
    }

    public DatabaseMigrationBuilder withPort(Integer port) {
        this.port = port;
        return this;
    }

    public DatabaseMigrationBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public DatabaseMigrationBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public DatabaseMigrationBuilder withTable(String table) {
        this.table = table;
        return this;
    }

    public DatabaseMigrationBuilder withDriver(DatabaseDriver databaseDriver) {
        this.databaseDriver = databaseDriver;
        return this;
    }

    public DatabaseMigrationBuilder withClasspath(String... locations) {
        this.classpaths = Arrays.asList(locations);
        return this;
    }

    public DatabaseMigrationBuilder migrate() {
        try (Connection connection = connection()) {
            DatabaseHistoryRepository databaseHistoryRepository = impl(connection);
            if (!databaseHistoryRepository.exists(database, table)) {
                logger.info("Table " + table + " not found in database " + database + " will be created");
                databaseHistoryRepository.create(database, table);
            } else {
                logger.info("Table " + table + " already exists in database " + database + ". Nothing to do.");
            }
            DatabaseHistory databaseHistory = databaseHistoryRepository.current(database, table);
            logger.info("Current version is {}", databaseHistory.getName());

            databaseHistoryRepository.init(database);

            for (DatabaseSqlResource databaseSqlResource : sqlResources()) {
                if (databaseSqlResource.getVersion() > databaseHistory.getVersion()) {
                    StringTokenizer stringTokenizer = new StringTokenizer(
                            new String(databaseSqlResource.getData(), "UTF-8"), ";");
                    while (stringTokenizer.hasMoreTokens()) {
                        String statement = stringTokenizer.nextToken();
                        if (!StringUtils.isEmpty(statement) && !StringUtils.isBlank(statement)) {
                            try (PreparedStatement preparedStatement = connection
                                    .prepareStatement(statement)) {
                                preparedStatement.execute();
                            }
                        }
                    }
                    logger.info("File {} was applyed successfully on database ", databaseSqlResource.getPath().getFileName());
                    databaseHistoryRepository.insert(database, table, databaseSqlResource.getPath().getFileName().toString(), databaseSqlResource.getVersion());
                } else {
                    logger.info("File {} was ignored because is lower or equals than current version {}", databaseSqlResource.getPath().getFileName().toString(), databaseHistory.getName());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private DatabaseHistoryRepository impl(Connection connection) {
        return new DatabaseHistoryRepositoryImpl(connection, driverQuery());
    }

    private DriverQuery driverQuery() {
        if (databaseDriver.equals(DatabaseDriver.H2)) {
            return new H2DriverQuery();
        } else if (databaseDriver.equals(DatabaseDriver.MYSQL)) {
            return new MysqlDriverQuery();
        } else if (databaseDriver.equals(DatabaseDriver.MARIADB)) {
            return new MariaDbDriverQuery();
        }
        throw new RuntimeException("Could not determine driver type");
    }

    private Connection connection() {
        try {
            String urlConnection = urlConnection();
            logger.info("url connection: {}, username: {}", urlConnection, username);
            properties.setProperty("user", username);
            properties.setProperty("password", password);
            return DriverManager.getConnection(urlConnection, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String urlConnection() {
        if (databaseDriver.equals(DatabaseDriver.MYSQL)) {
            return "jdbc:mysql://" + host + ":" + port + "/" + database;
        } else if (databaseDriver.equals(DatabaseDriver.MARIADB)) {
            return "jdbc:mariadb://" + host + ":" + port + "/" + database;
        } else if (databaseDriver.equals(DatabaseDriver.H2)) {
            return "jdbc:h2:tcp://" + host + ":" + port + "/." + location + "/" + database;
        }
        throw new RuntimeException("Could not build connection database url");
    }

    private Integer group(String group) {
        if (!StringUtils.isEmpty(group)) {
            return Integer.valueOf(StringUtils.rightPad(group, 3, '0'));
        } else {
            return 0;
        }
    }

    public List<DatabaseSqlResource> sqlResources() {
        return locations(this::fromLocation)
                .stream()
                .sorted(Comparator.comparing(DatabaseSqlResource::getVersion))
                .collect(Collectors.toList());
    }

    private List<DatabaseSqlResource> fromLocation(Path location) {
        try {
            return Files.walk(location).filter(path -> !Files.isDirectory(path)).map(path -> {
                try {
                    Matcher matcher = Pattern
                            .compile("v\\.([0-9]{1,})(\\.([0-9]{1,}))?(\\.([0-9]{1,}))?(\\.([0-9]{1,}))?")
                            .matcher(path.getFileName().toString());
                    if (matcher.find()) {
                        Integer group1 = group(matcher.group(1));
                        Integer group2 = group(matcher.group(3));
                        Integer group3 = group(matcher.group(5));
                        Integer group4 = group(matcher.group(7));
                        return new DatabaseSqlResource(path, Files.readAllBytes(path),
                                Long.valueOf(String.format("%03d%03d%03d%03d", group1, group2, group3, group4)));
                    }
                    throw new RuntimeException("File name not match pattern v.x.x.x.x (v.1,v.1.0,v.1.0.0,v.1.0.0.0");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<DatabaseSqlResource> locations(Function<Path, List<DatabaseSqlResource>> function) {
        return classpaths.stream().map(location -> {
                    try {
                        URI uri = getClass().getResource(location).toURI();
                        if (uri.getScheme().equals("jar")) {
                            try (FileSystem fileSystem = FileSystems.newFileSystem(uri,
                                    Collections.<String, Object>emptyMap())) {
                                return function.apply(fileSystem.getPath(location));
                            }
                        } else {
                            return function.apply(Paths.get(uri));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


}
