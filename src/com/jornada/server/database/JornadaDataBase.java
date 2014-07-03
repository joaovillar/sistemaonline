package com.jornada.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.jornada.ConfigJornada;

public class JornadaDataBase {

	@SuppressWarnings("unused")
	private static String driver, url, host, portNumber, database, userName,password, connectionUrl;
	private static Throwable initializationException;
	@SuppressWarnings("unused")
	private Statement stm;
	private static Connection connection;

	static {
		driver = ConfigJornada.getProperty("config.driver");
		url = ConfigJornada.getProperty("config.url");
		host = ConfigJornada.getProperty("config.host");
		portNumber = ConfigJornada.getProperty("config.portNumber");
		database = ConfigJornada.getProperty("config.database");
		userName = ConfigJornada.getProperty("config.userName");
		password = ConfigJornada.getProperty("config.password");
		connectionUrl = String.format("jdbc:postgresql://%s:%s/%s", host,portNumber, database);

		/* Open the connection */
		try {
			Class.forName(driver);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void close() {

		if (this.getConnection() != null) {
			try {
				connection.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public synchronized Connection getConnection() {
		if (connection == null) {
			if (JornadaDataBase.initializationException == null) {
				try {
					// create the connection
					connection = DriverManager.getConnection(JornadaDataBase.connectionUrl,
							JornadaDataBase.userName, JornadaDataBase.password);

					// TODO statement should not be created here (no where this
					// class)
					stm = connection.createStatement();
				} catch (SQLException e) {
					throw new RuntimeException(
							"Problem when trying to connect to the database. If connection info changes, you need to restart the application for the changes to tae effect.",
							e);
				}
			} else {
				throw new RuntimeException(
						"A problem happened when attempted to get connection information. If connection info changes, you need to restart the application for the changes to tae effect.",
						JornadaDataBase.initializationException);
			}
		}
		return connection;
	}

//	public Connection createConnection() {
//		JornadaDataBase mpConn = new JornadaDataBase();
//		Connection conn = mpConn.getConnection();
//		// connections.add(conn);
//		return conn;
//	}
	
	public void createConnection() {

	}

}
