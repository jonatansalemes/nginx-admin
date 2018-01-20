package com.jslsolucoes.nginx.admin.ui.standalone;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.undertow.WARArchive;

import com.jslsolucoes.nginx.admin.ui.config.Configuration;
import com.jslsolucoes.nginx.admin.ui.config.ConfigurationLoader;
import com.jslsolucoes.nginx.admin.ui.standalone.mode.Argument;
import com.jslsolucoes.nginx.admin.ui.standalone.mode.ArgumentMode;

public class Main {
	
	private Main(){
		
	}

	public static void main(String[] args) throws Exception {

		ArgumentMode argumentMode = new ArgumentMode(args);
		Argument argument = argumentMode.parse();

		if (!argument.getQuit()) {
			
			
			Properties properties = new Properties();
			properties.load(new FileInputStream(new File(argument.getConf())));
			
			Configuration configuration = ConfigurationLoader.buildFrom(properties);
			Swarm swarm = new Swarm(new String[] { "-Dswarm.http.port=" + configuration.getServer().getPort(),
					"-Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel",
					"\"-Dapplication.properties=" + argument.getConf()+ "\"",
					 "-Dversion=" + configuration.getApplication().getVersion(),
					    "-Durl.base=" + configuration.getApplication().getUrlBase(), 
					    "-Dmail.server=" + configuration.getSmtp().getHost(),
					    "-Dmail.port=" + configuration.getSmtp().getPort(), 
					    "-Dmail.tls=" + configuration.getSmtp().getTls(),
					    "\"-Dmail.from.name=" + configuration.getSmtp().getFromName() + "\"",
					    "-Dmail.from.address=" + configuration.getSmtp().getFromAddress(),
					    "-Dmail.authenticate=" + configuration.getSmtp().getAuthenticate(),
					    "-Dmail.username=" + configuration.getSmtp().getUserName(),
					    "-Dmail.password=" + configuration.getSmtp().getPassword(),
					    "-Derror.mail.mailing.list=" + configuration.getSmtp().getMailList().stream().collect(Collectors.joining(",")),
					    "\"-Derror.mail.subject=" + configuration.getSmtp().getSubject() + "\"",
					    "-Dmail.charset=" + configuration.getSmtp().getSubject() });
			swarm.fraction(new DatasourcesFraction()
			.jdbcDriver("com.oracle", (d) -> {
						d.driverClassName("oracle.jdbc.driver.OracleDriver");
						d.xaDatasourceClass("oracle.jdbc.xa.OracleXADataSource");
						d.driverModuleName("oracle");
			})		
			.dataSource("NginxAdminDataSource", ds -> {
				ds.jndiName("java:jboss/datasources/nginx-admin");
				ds.driverName(configuration.getDatabase().getDatabaseDriver().getDriverName());
				ds.connectionUrl(configuration.getDatabase().getConnectionUrl());
				ds.password(configuration.getDatabase().getPassword());
				ds.maxPoolSize(configuration.getDatabase().getDatabasePool().getMaxConnection());
				ds.minPoolSize(configuration.getDatabase().getDatabasePool().getMinConnection());
				ds.initialPoolSize(configuration.getDatabase().getDatabasePool().getInitialConnection());
			}));
			swarm.fraction(new LoggingFraction().consoleHandler("CONSOLE", f -> {
				f.level(Level.INFO);
				f.formatter("%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n");
			}).rootLogger(Level.ERROR, "CONSOLE"));
			swarm.start();

			InputStream war = Main.class.getResourceAsStream("/nginx-admin-ui-"+configuration.getApplication().getVersion()+".war");
			File file = File.createTempFile(UUID.randomUUID().toString(), ".war");
			FileUtils.copyInputStreamToFile(war, file);
			file.deleteOnExit();

			WARArchive warArchive = ShrinkWrap.createFromZipFile(WARArchive.class, file);
			swarm.deploy(warArchive);
		}

	}
}
