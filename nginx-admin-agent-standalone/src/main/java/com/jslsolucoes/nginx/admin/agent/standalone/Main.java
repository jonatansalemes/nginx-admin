package com.jslsolucoes.nginx.admin.agent.standalone;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.management.ManagementFraction;
import org.wildfly.swarm.undertow.UndertowFraction;
import org.wildfly.swarm.undertow.WARArchive;

import com.jslsolucoes.nginx.admin.agent.config.Configuration;
import com.jslsolucoes.nginx.admin.agent.config.ConfigurationLoader;
import com.jslsolucoes.nginx.admin.agent.standalone.mode.Argument;
import com.jslsolucoes.nginx.admin.agent.standalone.mode.ArgumentMode;

public class Main {

	private Main() {

	}

	public static void main(String[] args) throws Exception {

		ArgumentMode argumentMode = new ArgumentMode(args);
		Argument argument = argumentMode.parse();

		if (!argument.getQuit()) {

			Configuration configuration = ConfigurationLoader.newBuilder().withFile(argument.getConf()).build();

			File jks = copyToTemp("/keystore.jks");
			File war = copyToTemp("/nginx-admin-agent-" + configuration.getApplication().getVersion() + ".war");

			Swarm swarm = new Swarm(new String[] { "-Dswarm.http.port=" + configuration.getServer().getHttpPort(),
					"-Dswarm.https.port=" + configuration.getServer().getHttpsPort(),
					"-Dapplication.properties=" + argument.getConf() })
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

	private static File copyToTemp(String classpath) throws IOException {
		InputStream war = Main.class.getResourceAsStream(classpath);
		File file = File.createTempFile(UUID.randomUUID().toString(), "." + FilenameUtils.getExtension(classpath));
		FileUtils.copyInputStreamToFile(war, file);
		file.deleteOnExit();
		return file;
	}
}
