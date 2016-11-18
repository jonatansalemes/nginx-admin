package com.jslsolucoes.nginx.admin.standalone;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.undertow.WARArchive;

public class Main {

	public static void main(String[] args) throws Exception {
		Swarm swarm = new Swarm(args);
		swarm.fraction(new DatasourcesFraction().dataSource("AdminDS", (ds) -> {
			ds.driverName("h2");
			ds.connectionUrl("jdbc:h2:~/admin;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
			ds.userName("sa");
			ds.password("sa");
			ds.jndiName("java:jboss/datasources/AdminDS");
		}));
		swarm.start();

		InputStream war = Main.class.getResourceAsStream("/nginx-admin-ui.war");
		File file = File.createTempFile(UUID.randomUUID().toString(), ".war");
		FileUtils.copyInputStreamToFile(war, file);
		file.deleteOnExit();

		WARArchive warArchive = ShrinkWrap.createFromZipFile(WARArchive.class, file);
		swarm.deploy(warArchive);
	}
}
