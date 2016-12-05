package com.jslsolucoes.nginx.admin.standalone;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.undertow.WARArchive;

import com.jslsolucoes.nginx.admin.standalone.launcher.Launcher;
import com.jslsolucoes.nginx.admin.standalone.launcher.mode.ArgumentMode;
import com.jslsolucoes.nginx.admin.standalone.launcher.mode.LauncherMode;

public class Main {

	public static void main(String[] args) throws Exception {
		
		LauncherMode launchMode = new ArgumentMode(args);
		Launcher launcher = launchMode.launcher();
		
		if (!launcher.getQuit()) {
			args = new String []{"-Dswarm.http.port="+launcher.getPort(),
					"-Dswarm.bind.address="+launcher.getBind()};
			
			Swarm swarm = new Swarm(args);
			swarm.fraction(new DatasourcesFraction().dataSource("AdminDS", (ds) -> {
				ds.driverName("h2");
				ds.connectionUrl("jdbc:h2:~/nginx-admin/database;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
				ds.userName(launcher.getDataSource().getUserName());
				ds.password(launcher.getDataSource().getPassword());
				ds.jndiName("java:jboss/datasources/AdminDS");
			}));
			swarm.fraction(new LoggingFraction()
					.rootLogger(Level.ERROR));
			swarm.start();

			InputStream war = Main.class.getResourceAsStream("/nginx-admin-ui.war");
			File file = File.createTempFile(UUID.randomUUID().toString(), ".war");
			FileUtils.copyInputStreamToFile(war, file);
			file.deleteOnExit();

			WARArchive warArchive = ShrinkWrap.createFromZipFile(WARArchive.class, file);
			swarm.deploy(warArchive);
		}
		
	}
}
