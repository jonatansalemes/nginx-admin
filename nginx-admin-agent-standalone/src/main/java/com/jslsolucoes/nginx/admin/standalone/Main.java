package com.jslsolucoes.nginx.admin.standalone;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.undertow.WARArchive;

import com.jslsolucoes.nginx.admin.standalone.config.StandaloneConfiguration;
import com.jslsolucoes.nginx.admin.standalone.config.StandaloneConfigurationParser;
import com.jslsolucoes.nginx.admin.standalone.mode.Argument;
import com.jslsolucoes.nginx.admin.standalone.mode.ArgumentMode;

public class Main {
	
	private Main(){
		
	}

	public static void main(String[] args) throws Exception {

		ArgumentMode argumentMode = new ArgumentMode(args);
		Argument argument = argumentMode.parse();

		if (!argument.getQuit()) {
			
			StandaloneConfiguration standaloneConfiguration = StandaloneConfigurationParser.parse(argument.getConf());
			Swarm swarm = new Swarm(new String[] { "-Dswarm.http.port=" + standaloneConfiguration.getServer().getPort() });
			swarm.fraction(new LoggingFraction().consoleHandler("CONSOLE", f -> {
				f.level(Level.INFO);
				f.formatter("%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n");
			}).rootLogger(Level.ERROR, "CONSOLE"));
			swarm.start();

			InputStream war = Main.class.getResourceAsStream("/nginx-admin-agent-"+standaloneConfiguration.getApplication().getVersion()+".war");
			File file = File.createTempFile(UUID.randomUUID().toString(), ".war");
			FileUtils.copyInputStreamToFile(war, file);
			file.deleteOnExit();

			WARArchive warArchive = ShrinkWrap.createFromZipFile(WARArchive.class, file);
			swarm.deploy(warArchive);
		}

	}
}
