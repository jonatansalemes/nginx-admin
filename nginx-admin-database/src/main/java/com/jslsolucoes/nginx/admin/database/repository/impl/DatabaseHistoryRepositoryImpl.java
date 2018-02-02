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
	public Boolean exists(String database,String table) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(driverQuery.exists(database,table))){
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					return resultSet.getBoolean("found");
				}
			}
		} catch (SQLException e) {
			logger.error("Could not determine if table exists",e);
		}
		return false;
	}

	@Override
	public void create(String database, String table) {
		StringTokenizer stringTokenizer = new StringTokenizer(driverQuery.create(database,table),";");
		while(stringTokenizer.hasMoreTokens()) {
			String query = stringTokenizer.nextToken();
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				logger.error("Could not create table database statement " + query,e);
			}
		}
	}
	
	@Override
	public DatabaseHistory current(String database, String table) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(driverQuery.current(database,table))){
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) {
					return new DatabaseHistory(resultSet.getLong("id"),resultSet.getString("name"),resultSet.getLong("version"));
				}
			}
		} catch (SQLException e) {
			logger.error("Could calculate version",e);
		}
		return new DatabaseHistory("v.0.0.0",Long.valueOf(0));
	}

	@Override
	public void insert(String database, String table, String name, Long version) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(driverQuery.insert(database,table))){
			preparedStatement.setString(1,name);
			preparedStatement.setLong(2, version);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Could not determine if table exists",e);
		}
		
	}
}
