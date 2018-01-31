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

import com.jslsolucoes.nginx.admin.ui.config.Configuration;
import com.jslsolucoes.nginx.admin.ui.config.ConfigurationLoader;
import com.jslsolucoes.nginx.admin.ui.config.Database;
import com.jslsolucoes.nginx.admin.ui.config.DatabaseDriver;
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

			swarm.fraction(new DatasourcesFraction().jdbcDriver(jdbcDriver(configuration.getDatabase()))
					.dataSource("ExampleDS", dataSource -> {
						dataSource.driverName("com.h2database.h2");
						dataSource.jndiName("java:jboss/datasources/ExampleDS");
						dataSource.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
						dataSource.userName("sa");
						dataSource.password("sa");
					}).dataSource("NginxAdminDS", dataSource -> {
						dataSource.driverName(driverName(configuration.getDatabase()));
						dataSource.jndiName("java:jboss/datasources/nginx-admin");
						dataSource.connectionUrl(configuration.getDatabase().getUrlConnection());
						dataSource.userName(configuration.getDatabase().getUserName());
						
						if(!StringUtils.isEmpty(configuration.getDatabase().getPassword())) {
							dataSource.password(configuration.getDatabase().getPassword());
						}
						dataSource.maxPoolSize(configuration.getDatabase().getDatabasePool().getMaxConnection());
						dataSource.minPoolSize(configuration.getDatabase().getDatabasePool().getMinConnection());
						dataSource.initialPoolSize(configuration.getDatabase().getDatabasePool().getInitialConnection());
					})).fraction(new ManagementFraction().securityRealm("SSLRealm", securityRealm -> {
						securityRealm.sslServerIdentity(sslServerIdentity -> {
							sslServerIdentity.keystorePath(jks.getAbsolutePath()).keystorePassword("password")
									.alias("selfsigned");
						});
					})).fraction(new UndertowFraction().server("default-server", server -> {
						server.httpListener("default", httpListener -> {
							httpListener.socketBinding("http").redirectSocket("https").enableHttp2(true);
						}).httpsListener("https", httpsListener -> {
							httpsListener.securityRealm("SSLRealm").socketBinding("https").enableHttp2(true);
						}).host("default-host", host -> {
							host.alias("localhost");
						});
					}).bufferCache("default").servletContainer("default", servletContainer -> {
						servletContainer.websocketsSetting().jspSetting();
					})).fraction(new LoggingFraction().consoleHandler("CONSOLE", consoleHandler -> {
						consoleHandler.level(Level.INFO);
						consoleHandler.formatter("%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n");
					}).rootLogger(rootLogger -> {
						rootLogger.level(Level.ERROR);
						rootLogger.handler("CONSOLE");
					}));
			swarm.start();

			WARArchive warArchive = ShrinkWrap.createFromZipFile(WARArchive.class, war);
			swarm.deploy(warArchive);
		}
	}

	@SuppressWarnings("rawtypes")
	private static JDBCDriver jdbcDriver(Database database) {

		if (database.getDatabaseDriver().equals(DatabaseDriver.ORACLE)) {
			JDBCDriver jDBCDriver = new JDBCDriver("com.oracle");
			jDBCDriver.driverClassName("oracle.jdbc.driver.OracleDriver");
			jDBCDriver.xaDatasourceClass("oracle.jdbc.xa.OracleXADataSource");
			jDBCDriver.driverModuleName("com.oracle");
			jDBCDriver.driverName("com.oracle");
			return jDBCDriver;
		} else if (database.getDatabaseDriver().equals(DatabaseDriver.POSTGRESQL)) {
			JDBCDriver jDBCDriver = new JDBCDriver("org.postgresql");
			jDBCDriver.driverClassName("org.postgresql.Driver");
			jDBCDriver.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
			jDBCDriver.driverModuleName("org.postgresql");
			jDBCDriver.driverName("org.postgresql");
			return jDBCDriver;
		} else if (database.getDatabaseDriver().equals(DatabaseDriver.MYSQL)) {
			JDBCDriver jDBCDriver = new JDBCDriver("com.mysql");
			jDBCDriver.driverClassName("com.mysql.jdbc.Driver");
			jDBCDriver.xaDatasourceClass("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
			jDBCDriver.driverModuleName("com.mysql");
			jDBCDriver.driverName("com.mysql");
			return jDBCDriver;
		} else if (database.getDatabaseDriver().equals(DatabaseDriver.H2)) {
			JDBCDriver jDBCDriver = new JDBCDriver("com.h2database.h2");
			jDBCDriver.driverClassName("org.h2.Driver");
			jDBCDriver.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
			jDBCDriver.driverModuleName("com.h2database.h2");
			jDBCDriver.driverName("com.h2database.h2");
			return jDBCDriver;
		} else if (database.getDatabaseDriver().equals(DatabaseDriver.SQLSERVER)) {
			JDBCDriver jDBCDriver = new JDBCDriver("com.microsoft.sqlserver");
			jDBCDriver.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			jDBCDriver.xaDatasourceClass("com.microsoft.sqlserver.jdbc.SQLServerXADataSource");
			jDBCDriver.driverModuleName("com.microsoft.sqlserver");
			jDBCDriver.driverName("com.microsoft.sqlserver");
			return jDBCDriver;
		}
		throw new RuntimeException("Could not resolve jdbc driver");
	}

	private static String driverName(Database database) {
		if (database.getDatabaseDriver().equals(DatabaseDriver.ORACLE)) {
			return "com.oracle";
		} else if (database.getDatabaseDriver().equals(DatabaseDriver.POSTGRESQL)) {
			return "org.postgresql";
		} else if (database.getDatabaseDriver().equals(DatabaseDriver.MYSQL)) {
			return "com.mysql";
		} else if (database.getDatabaseDriver().equals(DatabaseDriver.H2)) {
			return "com.h2database.h2";
		} else if (database.getDatabaseDriver().equals(DatabaseDriver.SQLSERVER)) {
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
	// cd d:/workspace/github/nginx-admin/nginx-admin-ui-standalone/target
	// java -server -Djava.net.preferIPv4Stack=true -Xms256m -Xmx1g -jar nginx-admin-ui-standalone-2.0.0-swarm.jar -c "D:\workspace\github\nginx-admin\nginx-admin-ui-standalone\nginx-admin\conf\nginx-admin.conf"
	// java -server -Djava.net.preferIPv4Stack=true -Xms256m -Xmx1g -jar nginx-admin-ui-standalone-2.0.0-swarm.jar -c "/hd1/workspace/github/nginx-admin/nginx-admin-ui-standalone/nginx-admin/conf/nginx-admin.conf"

}
