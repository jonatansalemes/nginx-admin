package com.jslsolucoes.nginx.admin.database;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.h2.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.database.model.DatabaseHistory;
import com.jslsolucoes.nginx.admin.database.repository.DatabaseHistoryRepository;
import com.jslsolucoes.nginx.admin.database.repository.impl.DatabaseHistoryRepositoryImpl;
import com.jslsolucoes.nginx.admin.database.repository.impl.driver.DriverQuery;
import com.jslsolucoes.nginx.admin.database.repository.impl.driver.H2DriverQuery;

public class DatabaseMigrate {

	private String urlConnection;
	private String username;
	private String password;
	private String driver;
	private String schema = "public";
	private String tableName = "db_migrate_history";
	private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrate.class);
	private List<String> classpaths;

	static {
		try {
			DriverManager.registerDriver(new Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private DatabaseMigrate() {

	}

	public static DatabaseMigrate newBuilder() {
		return new DatabaseMigrate();
	}

	public DatabaseMigrate withUrlConnection(String urlConnection) {
		this.urlConnection = urlConnection;
		return this;
	}

	public DatabaseMigrate withUsername(String username) {
		this.username = username;
		return this;
	}

	public DatabaseMigrate withPassword(String password) {
		this.password = password;
		return this;
	}

	public DatabaseMigrate withSchema(String schema) {
		this.schema = schema;
		return this;
	}

	public DatabaseMigrate withTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public DatabaseMigrate withDriver(String driver) {
		this.driver = driver;
		return this;
	}

	public DatabaseMigrate withClasspath(String... locations) {
		this.classpaths = Arrays.asList(locations);
		return this;
	}

	public DatabaseMigrate migrate() {
		try (Connection connection = connection()) {
			DatabaseHistoryRepository databaseHistoryRepository = impl(connection);
			if (!databaseHistoryRepository.exists(schema, tableName)) {
				logger.info("Table " + tableName + " not found in schema " + schema + " will be created");
				databaseHistoryRepository.create(schema, tableName);
			} else {
				logger.info("Table " + tableName + " already exists in schema " + schema + ". Nothing to do.");
			}
			DatabaseHistory databaseHistory = databaseHistoryRepository.current(schema, tableName);
			logger.info("Current version is {}",databaseHistory.getName());
			for (DatabaseSqlResource fileSequence : files()) {
				if (fileSequence.getVersion() > databaseHistory.getVersion()) {
					StringTokenizer stringTokenizer = new StringTokenizer(
							new String(Files.readAllBytes(fileSequence.getPath()), "UTF-8"), ";");
					while (stringTokenizer.hasMoreTokens()) {
						try (PreparedStatement preparedStatement = connection
								.prepareStatement(stringTokenizer.nextToken())) {
							preparedStatement.execute();
						}
					}
					logger.info("File {} was applyed successfully on database ",fileSequence.getPath().getFileName());
					databaseHistoryRepository.insert(schema, tableName,fileSequence.getPath().getFileName().toString(),fileSequence.getVersion());
				} else {
					logger.info("File {} was ignored because is lower than current version {}",fileSequence.getPath().getFileName().toString(),databaseHistory.getName());
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
		if (driver.equals("h2")) {
			return new H2DriverQuery();
		}
		throw new RuntimeException("Could not determine driver type");
	}

	private Connection connection() {
		try {
			logger.info("url connection: {}, username: {}", urlConnection, username);
			return DriverManager.getConnection(urlConnection, username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Integer group(String group) {
		if (!StringUtils.isEmpty(group)) {
			return Integer.valueOf(StringUtils.rightPad(group, 3, '0'));
		} else {
			return 0;
		}
	}

	public List<DatabaseSqlResource> files() {
		return locations().stream().flatMap(location -> {
			try {
				return Files.walk(location).filter(path -> !Files.isDirectory(path)).map(path -> {
					Matcher matcher = Pattern
							.compile("v\\.([0-9]{1,})(\\.([0-9]{1,}))?(\\.([0-9]{1,}))?(\\.([0-9]{1,}))?")
							.matcher(path.getFileName().toString());
					if (matcher.find()) {
						Integer group1 = group(matcher.group(1));
						Integer group2 = group(matcher.group(3));
						Integer group3 = group(matcher.group(5));
						Integer group4 = group(matcher.group(7));
						return new DatabaseSqlResource(path,
								Long.valueOf(String.format("%03d%03d%03d%03d", group1, group2, group3, group4)));
					}
					throw new RuntimeException("File name not match pattern v.x.x.x.x (v.1,v.1.0,v.1.0.0,v.1.0.0.0");
				}).collect(Collectors.toList()).stream();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).sorted((c1, c2) -> {
			return c1.getVersion().compareTo(c2.getVersion());
		}).collect(Collectors.toList());

	}

	private List<Path> locations() {
		return classpaths.stream().map(location -> {
			try {
				URI uri = getClass().getResource(location).toURI();
				if (uri.getScheme().equals("jar")) {
					try (FileSystem fileSystem = FileSystems.newFileSystem(uri,
							Collections.<String, Object>emptyMap())) {
						return fileSystem.provider().getPath(uri);
					}
				} else {
					return Paths.get(uri);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
	}
}
