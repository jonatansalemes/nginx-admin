package com.jslsolucoes.nginx.admin.ui.standalone;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.datasources.JDBCDriver;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.management.ManagementFraction;
import org.wildfly.swarm.undertow.UndertowFraction;
import org.wildfly.swarm.undertow.WARArchive;

import com.jslsolucoes.nginx.admin.database.DatabaseDriver;
import com.jslsolucoes.nginx.admin.database.DatabaseMigrationBuilder;
import com.jslsolucoes.nginx.admin.ui.config.Configuration;
import com.jslsolucoes.nginx.admin.ui.config.ConfigurationLoader;
import com.jslsolucoes.nginx.admin.ui.config.Database;
import com.jslsolucoes.nginx.admin.ui.standalone.mode.Argument;
import com.jslsolucoes.nginx.admin.ui.standalone.mode.ArgumentMode;
import com.microsoft.sqlserver.jdbc.StringUtils;

public class Main {

	private Main() {

	}

	public static void main(String[] args) throws Exception {

		ArgumentMode argumentMode = new ArgumentMode(args);
		Argument argument = argumentMode.parse();

		if (!argument.getQuit()) {

			Configuration configuration = ConfigurationLoader.newBuilder().withFile(argument.getConf()).build();

			File jks = copyToTemp("/keystore.jks");
			File war = copyToTemp("/nginx-admin-ui-" + configuration.getApplication().getVersion() + ".war");

			Swarm swarm = new Swarm(new String[] { "-Dswarm.http.port=" + configuration.getServer().getHttpPort(),
					"-Dswarm.https.port=" + configuration.getServer().getHttpsPort(),
					"-Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel",
					"-Dapplication.properties=" + argument.getConf(),
					"-Durl.base=" + configuration.getApplication().getUrlBase(),
					"-Dmail.server=" + configuration.getSmtp().getHost(),
					"-Dmail.port=" + configuration.getSmtp().getPort(),
					"-Dmail.tls=" + configuration.getSmtp().getTls(),
					"-Dmail.from.name=" + configuration.getSmtp().getFromName(),
					"-Dmail.from.address=" + configuration.getSmtp().getFromAddress(),
					"-Dmail.authenticate=" + configuration.getSmtp().getAuthenticate(),
					"-Dmail.username=" + configuration.getSmtp().getUserName(),
					"-Dmail.password=" + configuration.getSmtp().getPassword(),
					"-Dmail.charset=" + configuration.getSmtp().getCharset() });

			
			DatasourcesFraction datasourcesFraction = new DatasourcesFraction().jdbcDriver(jdbcH2Driver());
			if(!configuration.getDatabase().getDriver().equals("h2")) {
				datasourcesFraction = datasourcesFraction.jdbcDriver(jdbcDriver(configuration.getDatabase()));
			}
			swarm.fraction(datasourcesFraction
					.dataSource("ExampleDS", dataSource -> {
						dataSource.driverName("com.h2database.h2");
						dataSource.jndiName("java:jboss/datasources/ExampleDS");
						dataSource.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
						dataSource.userName("sa");
						dataSource.password("sa");
					}).dataSource("NginxAdminDS", dataSource -> {
						dataSource.driverName(driverName(configuration.getDatabase()));
						dataSource.jndiName("java:jboss/datasources/nginx-admin");
						dataSource.connectionUrl(urlConnection(configuration.getDatabase()));
						dataSource.userName(configuration.getDatabase().getUsername());
						if (!StringUtils.isEmpty(configuration.getDatabase().getPassword())) {
							dataSource.password(configuration.getDatabase().getPassword());
						}
						dataSource.maxPoolSize(configuration.getDatabase().getDatabasePool().getMaxConnection());
						dataSource.minPoolSize(configuration.getDatabase().getDatabasePool().getMinConnection());
						dataSource
								.initialPoolSize(configuration.getDatabase().getDatabasePool().getInitialConnection());
					})).fraction(new ManagementFraction().securityRealm("SSLRealm", securityRealm -> {
						securityRealm.sslServerIdentity(sslServerIdentity -> {
							sslServerIdentity.keystorePath(jks.getAbsolutePath()).keystorePassword("password")
									.alias("selfsigned");
						});
					})).fraction(new UndertowFraction().server("default-server", server -> {
						server.httpListener("default", httpListener -> {
							httpListener.socketBinding("http").redirectSocket("https").enableHttp2(true).maxPostSize(maxPostSize());
						}).httpsListener("https", httpsListener -> {
							httpsListener
							.securityRealm("SSLRealm").socketBinding("https").enableHttp2(true).maxPostSize(maxPostSize());
						}).host("default-host", host -> {
							host.alias("localhost");
						});
					}).bufferCache("default").servletContainer("default", servletContainer -> {
						servletContainer.websocketsSetting().jspSetting();
					})).fraction(new LoggingFraction()
					.consoleHandler("console", consoleHandler -> {
						consoleHandler.level(Level.INFO);
						consoleHandler.formatter("%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n");
					}).logger("com.jslsolucoes.nginx.admin.database", logger->{
						logger.category("com.jslsolucoes.nginx.admin.database");
						logger.level(Level.INFO);
						logger.useParentHandlers(false);
						logger.handler("console");
					}).rootLogger(rootLogger -> {
						rootLogger.level(Level.ERROR);
						rootLogger.handler("console");
					}));
			swarm.start();
			
			migrateDatabase(configuration.getDatabase());

			WARArchive warArchive = ShrinkWrap.createFromZipFile(WARArchive.class, war);
			swarm.deploy(warArchive);
		}
	}

	private static Long maxPostSize() {
		return Long.valueOf(30 * 1024 * 1024);
	}

	@SuppressWarnings("rawtypes")
	private static JDBCDriver jdbcH2Driver() {
		JDBCDriver jDBCDriver = new JDBCDriver("com.h2database.h2");
		jDBCDriver.driverClassName("org.h2.Driver");
		jDBCDriver.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
		jDBCDriver.driverModuleName("com.h2database.h2");
		jDBCDriver.driverName("com.h2database.h2");
		return jDBCDriver;
	}

	private static void migrateDatabase(Database database) {
		DatabaseMigrationBuilder.newBuilder().withClasspath("/db/migration/" + database.getDriver())
				.withDriver(DatabaseDriver.forName(database.getDriver())).withHost(database.getHost())
				.withLocation(database.getLocation())
				.withPort(database.getPort()).withDatabase(database.getName()).withUsername(database.getUsername())
				.withPassword(database.getPassword()).migrate();
	}

	@SuppressWarnings("rawtypes")
	private static JDBCDriver jdbcDriver(Database database) {

		String driver = database.getDriver();
		if (driver.equals("oracle")) {
			JDBCDriver jDBCDriver = new JDBCDriver("com.oracle");
			jDBCDriver.driverClassName("oracle.jdbc.driver.OracleDriver");
			jDBCDriver.xaDatasourceClass("oracle.jdbc.xa.OracleXADataSource");
			jDBCDriver.driverModuleName("com.oracle");
			jDBCDriver.driverName("com.oracle");
			return jDBCDriver;
		} else if (driver.equals("postgresql")) {
			JDBCDriver jDBCDriver = new JDBCDriver("org.postgresql");
			jDBCDriver.driverClassName("org.postgresql.Driver");
			jDBCDriver.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
			jDBCDriver.driverModuleName("org.postgresql");
			jDBCDriver.driverName("org.postgresql");
			return jDBCDriver;
		} else if (driver.equals("mysql")) {
			JDBCDriver jDBCDriver = new JDBCDriver("com.mysql");
			jDBCDriver.driverClassName("com.mysql.jdbc.Driver");
			jDBCDriver.xaDatasourceClass("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
			jDBCDriver.driverModuleName("com.mysql");
			jDBCDriver.driverName("com.mysql");
			return jDBCDriver;
		} else if (driver.equals("mariadb")) {
			JDBCDriver jDBCDriver = new JDBCDriver("org.mariadb");
			jDBCDriver.driverClassName("org.mariadb.jdbc.Driver");
			jDBCDriver.xaDatasourceClass("org.mariadb.jdbc.MySQLDataSource");
			jDBCDriver.driverModuleName("org.mariadb");
			jDBCDriver.driverName("org.mariadb");
			return jDBCDriver;
		} else if (driver.equals("sqlserver")) {
			JDBCDriver jDBCDriver = new JDBCDriver("com.microsoft.sqlserver");
			jDBCDriver.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			jDBCDriver.xaDatasourceClass("com.microsoft.sqlserver.jdbc.SQLServerXADataSource");
			jDBCDriver.driverModuleName("com.microsoft.sqlserver");
			jDBCDriver.driverName("com.microsoft.sqlserver");
			return jDBCDriver;
		}
		throw new RuntimeException("Could not resolve jdbc driver for " + driver);
	}

	private static String urlConnection(Database database) {
		String driver = database.getDriver();
		if (driver.equals("oracle")) {
			return "jdbc:oracle:thin:@" + database.getHost() + ":" + database.getPort() + "/" + database.getName();
		} else if (driver.equals("postgresql")) {
			return "jdbc:postgresql://" + database.getHost() + ":" + database.getPort() + "/" + database.getName();
		} else if (driver.equals("mysql")) {
			return "jdbc:mysql://" + database.getHost() + ":" + database.getPort() + "/" + database.getName() + "?useSSL=false";
		} else if (driver.equals("mariadb")) {
			return "jdbc:mariadb://" + database.getHost() + ":" + database.getPort() + "/" + database.getName() + "?useSSL=false";
		} else if (driver.equals("h2")) {
			return "jdbc:h2:" + database.getLocation() + "/" + database.getName() + ";INIT=use "+database.getName()+";AUTO_SERVER=TRUE";
		} else if (driver.equals("sqlserver")) {
			return "jdbc:sqlserver://" + database.getHost() + ":" + database.getPort() + "/" + database.getName();
		}
		throw new RuntimeException("Could not build connection database url");
	}

	private static String driverName(Database database) {
		String driver = database.getDriver();
		if (driver.equals("oracle")) {
			return "com.oracle";
		} else if (driver.equals("postgresql")) {
			return "org.postgresql";
		} else if (driver.equals("mysql")) {
			return "com.mysql";
		} else if (driver.equals("mariadb")) {
			return "org.mariadb";
		} else if (driver.equals("h2")) {
			return "com.h2database.h2";
		} else if (driver.equals("sqlserver")) {
			return "com.microsoft.sqlserver";
		}
		throw new RuntimeException("Could not resolve driver name");
	}

	private static File copyToTemp(String classpath) throws IOException {
		InputStream war = Main.class.getResourceAsStream(classpath);
		File file = File.createTempFile(UUID.randomUUID().toString(), "." + FilenameUtils.getExtension(classpath));
		FileUtils.copyInputStreamToFile(war, file);
		file.deleteOnExit();
		return file;
	}
}
