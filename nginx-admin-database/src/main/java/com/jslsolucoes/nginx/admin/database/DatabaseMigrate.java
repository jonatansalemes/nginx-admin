package com.jslsolucoes.nginx.admin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public DatabaseMigrate migrate() {
		try(Connection connection = connection()) {
			DatabaseHistoryRepository databaseHistoryRepository = impl(connection);
			if(!databaseHistoryRepository.exists(schema, tableName)) {
				logger.info("Table " + tableName + " not found in schema " + schema + " will be created");
				databaseHistoryRepository.create(schema, tableName);
			} else {
				logger.info("Table " + tableName + " already exists in schema " + schema + ". Nothing to do.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	private DatabaseHistoryRepository impl(Connection connection) {
		return new DatabaseHistoryRepositoryImpl(connection, driverQuery());
	}

	private DriverQuery driverQuery() {
		if(driver.equals("h2")) {
			return new H2DriverQuery();
		}
		throw new RuntimeException("Could not determine driver type");
	}

	private Connection connection() {
		try {
			logger.info("url connection: {}, username: {}",urlConnection,username);
			return DriverManager.getConnection(urlConnection, username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/*
	private Path path() throws URISyntaxException, IOException {
		URI uri = getClass().getResource("/db/migration/"+driver).toURI();
		if(uri.getScheme().equals("file")) {
		  return Paths.get(uri);
		} else if(uri.getScheme().equals("jar")) {
			try(FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String,Object>emptyMap())) {
				Path to = Files.createTempDirectory(UUID.randomUUID().toString());
				Path from = fileSystem.provider().getPath(uri);
				Files.walk(from).forEach(source->{
					
					final Path target = to.resolve(from.relativize(source).toString());
					try {
						if (Files.isDirectory(source)) {
			            	if(Files.notExists(target)){
			                	Files.createDirectories(target);
			            	}
			            } else {
			                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			            }
					 } catch (IOException e) {
				           throw new RuntimeException("Failed to unzip file.", e);
				     }
				});
				return to;
			}
		}
		return null;
	}
	
	public DatabaseMigrate migrate() {
		
		try(Connection connection = DriverManager.getConnection(urlConnection, username, password)) {
			List<Path> paths = Files
			.walk(path())
			.filter(path -> !Files.isDirectory(path))
			.map(path -> {
				Matcher matcher = Pattern.compile("v\\.([0-9]{1,})(\\.([0-9]{1,}))?(\\.([0-9]{1,}))?(\\.([0-9]{1,}))?").matcher(path.getFileName().toString());
				if(matcher.find()){
					Integer group1 = Integer.valueOf(StringUtils.rightPad(matcher.group(1),3,'0'));
					Integer group2 = 0;
					if(matcher.group(3) != null) {
						group2 = Integer.valueOf(StringUtils.rightPad(matcher.group(3),3,'0'));
					}
					Integer group3 = 0;
					if(matcher.group(5) != null) {
						group3 = Integer.valueOf(StringUtils.rightPad(matcher.group(5),3,'0'));
					}
					Integer group4 = 0;
					if(matcher.group(7) != null) {
						group4 = Integer.valueOf(StringUtils.rightPad(matcher.group(7),3,'0'));
					}
					return new FileSequence(path, Long.valueOf(String.format("%03d%03d%03d%03d",group1,group2,group3,group4)));
				}
				return null;
			})
			.sorted()
			.map(FileSequence::getPath).collect(Collectors.toList());
			for(Path path : paths) {
				StringTokenizer stringTokenizer = new StringTokenizer(new String(Files.readAllBytes(path),"UTF-8"), ";");
				while(stringTokenizer.hasMoreTokens()){
					try(PreparedStatement preparedStatement = connection.prepareStatement(stringTokenizer.nextToken())) {
						preparedStatement.execute();
						preparedStatement.close();
					}
				}
				System.out.println("Read and run " + path.getFileName());
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return this;
	}
	*/
}
