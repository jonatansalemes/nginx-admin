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
package com.jslsolucoes.nginx.admin.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

public class ConnectionFactory {

	@Resource(lookup = "java:jboss/datasources/nginx-admin")
	private DataSource dataSource;

	@Produces
	@RequestScoped
	public Connection getInstance() throws SQLException {
		return dataSource.getConnection();
	}

	public void close(@Disposes Connection connection) throws SQLException {
		if (!connection.isClosed()) {
			connection.close();
		}
	}

}
