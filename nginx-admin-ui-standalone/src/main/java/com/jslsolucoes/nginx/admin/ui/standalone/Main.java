package com.jslsolucoes.nginx.admin.ui.standalone;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
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
			
			swarm.fraction(new DatasourcesFraction()
			.jdbcDriver("com.microsoft.sqlserver", (d) -> {
				d.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				d.xaDatasourceClass("com.microsoft.sqlserver.jdbc.SQLServerXADataSource");
				d.driverModuleName("com.microsoft.sqlserver");
			})
			.jdbcDriver("com.oracle", (d) -> {
				d.driverClassName("oracle.jdbc.driver.OracleDriver");
				d.xaDatasourceClass("oracle.jdbc.xa.OracleXADataSource");
				d.driverModuleName("com.oracle");
			}).jdbcDriver("org.postgresql", (d) -> {
				d.driverClassName("org.postgresql.Driver");
				d.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
				d.driverModuleName("org.postgresql");
			}).jdbcDriver("com.mysql", (d) -> {
				d.driverClassName("com.mysql.jdbc.Driver");
				d.xaDatasourceClass("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
				d.driverModuleName("com.mysql");
			}).jdbcDriver("com.h2database.h2", (d) -> {
				d.driverClassName("org.h2.Driver");
				d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
				d.driverModuleName("com.h2database.h2");
			}).dataSource("ExampleDS",ds -> {
				ds.driverName("com.h2database.h2");
				ds.jndiName("java:jboss/datasources/ExampleDS");
				ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
				ds.userName("sa");
				ds.password("sa");
			}).dataSource("NginxAdminDS", ds -> {
				ds.driverName(driverName(configuration.getDatabase()));
				ds.jndiName("java:jboss/datasources/nginx-admin");
				ds.connectionUrl(connectionUrl(configuration.getDatabase()));
				ds.userName(configuration.getDatabase().getUserName());
				ds.password(configuration.getDatabase().getPassword());
				ds.maxPoolSize(configuration.getDatabase().getDatabasePool().getMaxConnection());
				ds.minPoolSize(configuration.getDatabase().getDatabasePool().getMinConnection());
				ds.initialPoolSize(configuration.getDatabase().getDatabasePool().getInitialConnection());
			}))
			.fraction(new ManagementFraction().securityRealm("SSLRealm", realm -> {
				realm.sslServerIdentity(sslServerIdentity -> {
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
			})).fraction(new LoggingFraction().consoleHandler("CONSOLE", f -> {
				f.level(Level.INFO);
				f.formatter("%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n");
			}).rootLogger(rootLogger -> {
				rootLogger.level(Level.ERROR);
				rootLogger.handler("CONSOLE");
			}));
			swarm.start();
			
			WARArchive warArchive = ShrinkWrap.createFromZipFile(WARArchive.class, war);
			swarm.deploy(warArchive);
		}

	}
	
	private static String driverName(Database database) {
		if (database.getDatabaseDriver().equals(DatabaseDriver.MYSQL)) { 
			return "com.mysql";
		}
		throw new RuntimeException("Could not resolve driver name");
	}

	private static String connectionUrl(Database database) {
			if (database.getDatabaseDriver().equals(DatabaseDriver.H2)) {
				return "jdbc:h2:" + database.getLocation() + ";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
			} else if (database.getDatabaseDriver().equals(DatabaseDriver.MYSQL)) {
				return "jdbc:mysql://"+database.getHost()+":"+database.getPort()+"/"+database.getName()+"?useSSL=false";
			}
			throw new RuntimeException("Could not build connection database url");
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
}
