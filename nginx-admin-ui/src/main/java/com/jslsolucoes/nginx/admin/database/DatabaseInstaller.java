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
package com.jslsolucoes.nginx.admin.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.annotation.Application;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;
import com.jslsolucoes.nginx.admin.repository.impl.ConfigurationType;

import br.com.caelum.vraptor.events.VRaptorInitialized;

public class DatabaseInstaller {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseInstaller.class);

	@Inject
	private Connection connection;

	@Inject
	private ConfigurationRepository configurationRepository;

	@Inject
	@Application
	private Properties properties;

	public void contextInitialized(@Observes VRaptorInitialized vRaptorInitialized) throws IOException {

		Integer installedVersion = 0;
		try {
			installedVersion = configurationRepository.getInteger(ConfigurationType.DB_VERSION);
		} catch (Exception exception) {
			logger.info("Database not installed yet. Installing ...");
			exception.printStackTrace();
		}

		Integer actualVersion = Integer.valueOf(properties.getProperty("db.version"));
		while (installedVersion < actualVersion) {
			Arrays.asList(resource("/sql/" + (++installedVersion) + ".sql").split(";")).stream()
					.filter(StringUtils::isNotEmpty).forEach(statement -> {
						try {
							PreparedStatement preparedStatement = connection.prepareStatement(statement);
							preparedStatement.executeUpdate();
							preparedStatement.close();
						} catch (SQLException sqlException) {
							throw new RuntimeException(sqlException);
						}
					});
		}
		logger.info("Database is up to date ...");
	}

	private String resource(String path) throws IOException {
		return IOUtils.toString(getClass().getResourceAsStream(path), "UTF-8");
	}
}