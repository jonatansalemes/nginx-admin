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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.annotation.Application;

import br.com.caelum.vraptor.events.VRaptorInitialized;

public class ApplicationContextListener {

	@Inject
	private Connection connection;
	
	private Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);
	
	@Inject
	@Application
	private Properties properties;

	public void contextInitialized(@Observes VRaptorInitialized vRaptorInitialized) throws SQLException, IOException {
		Integer installedVersion = installed();
		Integer actualVersion = Integer.valueOf(properties.getProperty("db.version"));
		while(installedVersion < actualVersion){
			String sql = IOUtils.toString(getClass().getResourceAsStream("/sql/"+(installedVersion+1)+".sql"),"UTF-8");
			StringTokenizer stringTokenizer = new StringTokenizer(sql,";");
			while(stringTokenizer.hasMoreTokens()){
				String query = stringTokenizer.nextToken().trim();
				if(!StringUtils.isEmpty(query)){
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.executeUpdate();
					preparedStatement.close();
				}
			}
			installedVersion = installed();
		}
	}
	
	private Integer installed() {
		Integer version = 0;
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement("select value from admin.configuration where variable = ?");
			preparedStatement.setString(1, "DB_VERSION");
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				version = resultSet.getInt("VALUE");
			}
			resultSet.close();
			preparedStatement.close();
		} catch(SQLException sqlException){
			logger.info("Database schema not found .. Prepare to create in next step ..");
		}
		return version;
	}
}