package com.jslsolucoes.nginx.admin.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class DatabaseChecker {
	
	private DataSource dataSource;

	public DatabaseChecker(DataSource dataSource) {
		this.dataSource = dataSource;
		
	}

	public void check() {
		try {
			Connection connection = dataSource.getConnection();
			install(connection);
			connection.close();
		} catch(Exception exception){
			System.out.println("Could not connect with database");
		}
	}

	private Integer installedVersion(Connection connection) {
		try {
			Integer version = 0;
			PreparedStatement preparedStatement = connection.prepareStatement("select value from admin.configuration where variable = ?");
			preparedStatement.setString(1, "VERSION");
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				version = resultSet.getInt("VALUE");
			}
			resultSet.close();
			preparedStatement.close();
			return version;
		} catch(Exception exception) {
			return 0;
		}
	}

	private Integer actualVersion(Connection connection) {
		try {
			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("/application.properties"));
			return Integer.valueOf(properties.getProperty("db.version"));
		} catch (Exception exception){
			exception.printStackTrace();
			return 1;
		}
	}

	private void install(Connection connection) {
		Integer installedVersion = installedVersion(connection);
		Integer actualVersion = actualVersion(connection);
		while(installedVersion < actualVersion){
			try {
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
				System.out.println("Database installed");
			} catch (Exception exception){
				exception.printStackTrace();
				System.out.println("Could not install database file "+(installedVersion+1)+".sql");
			}
			installedVersion = installedVersion(connection);
			System.out.println("Database version "+ installedVersion + " installed");
		}		
		System.out.println("Database installed or updated");
	}
}
