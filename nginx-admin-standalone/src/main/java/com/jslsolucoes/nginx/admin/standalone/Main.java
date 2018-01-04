/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com

 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
			
			Swarm swarm = new Swarm(new String[] { "-Dswarm.http.port=" + standaloneConfiguration.getServer().getPort(),
					"-Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel" });
			swarm.fraction(new DatasourcesFraction()
			.jdbcDriver("com.oracle", (d) -> {
						d.driverClassName("oracle.jdbc.driver.OracleDriver");
						d.xaDatasourceClass("oracle.jdbc.xa.OracleXADataSource");
						d.driverModuleName("oracle");
			})		
			.dataSource("NginxAdminDataSource", ds -> {
				ds.jndiName("java:jboss/datasources/nginx-admin");
				ds.driverName(standaloneConfiguration.getDatabase().getDatabaseDriver().getDriverName());
				ds.connectionUrl(standaloneConfiguration.getDatabase().getConnectionUrl());
				ds.password(standaloneConfiguration.getDatabase().getPassword());
				ds.maxPoolSize(standaloneConfiguration.getDatabase().getDatabasePool().getMaxConnection());
				ds.minPoolSize(standaloneConfiguration.getDatabase().getDatabasePool().getMinConnection());
				ds.initialPoolSize(standaloneConfiguration.getDatabase().getDatabasePool().getInitialConnection());
			}));
			swarm.fraction(new LoggingFraction().consoleHandler("CONSOLE", f -> {
				f.level(Level.INFO);
				f.formatter("%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n");
			}).rootLogger(Level.ERROR, "CONSOLE"));
			swarm.start();

			InputStream war = Main.class.getResourceAsStream("/nginx-admin-ui-"+standaloneConfiguration.getApplication().getVersion()+".war");
			File file = File.createTempFile(UUID.randomUUID().toString(), ".war");
			FileUtils.copyInputStreamToFile(war, file);
			file.deleteOnExit();

			WARArchive warArchive = ShrinkWrap.createFromZipFile(WARArchive.class, file);
			swarm.deploy(warArchive);
		}

	}
}
