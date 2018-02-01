package com.jslsolucoes.nginx.admin.database.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jslsolucoes.nginx.admin.database.model.DatabaseHistory;
import com.jslsolucoes.nginx.admin.database.repository.DatabaseHistoryRepository;
import com.jslsolucoes.nginx.admin.database.repository.impl.driver.DriverQuery;

public class DatabaseHistoryRepositoryImpl implements DatabaseHistoryRepository {

	private static Logger logger = LoggerFactory.getLogger(DatabaseHistoryRepositoryImpl.class);
	private DriverQuery driverQuery;
	private Connection connection;
	
	public DatabaseHistoryRepositoryImpl(Connection connection,DriverQuery driverQuery) {
		this.connection = connection;
		this.driverQuery = driverQuery;
	}

	@Override
	public Boolean exists(String schema,String tableName) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(driverQuery.exists(schema,tableName))){
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					Boolean found = resultSet.getBoolean("found");
					logger.info("Found result {}",found);
					return found;
				}
			}
		} catch (SQLException e) {
			logger.error("Could not determine if table exists",e);
		}
		return false;
	}

	@Override
	public void create(String schema, String tableName) {
		StringTokenizer stringTokenizer = new StringTokenizer(driverQuery.create(schema,tableName),";");
		while(stringTokenizer.hasMoreTokens()) {
			String query = stringTokenizer.nextToken();
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				logger.error("Could not execute statement " + query,e);
			}
		}
	}

	@Override
	public DatabaseHistory last(String schema, String tableName) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(driverQuery.last(schema,tableName))){
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					Long id = resultSet.getLong("id");
					String name = resultSet.getString("name");
					Long version = resultSet.getLong("version");
					return new DatabaseHistory(id, name, version);
				}
			}
		} catch (SQLException e) {
			logger.error("Could not create table",e);
		}
		return null;
	}
}
