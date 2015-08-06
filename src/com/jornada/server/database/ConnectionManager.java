package com.jornada.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jornada.ConfigJornada;

public class ConnectionManager {
	
	
//	private static boolean boneCPConnection = Boolean.parseBoolean(ConfigJornada.getProperty("use.boneCP.connection"));
	private static final String driver = ConfigJornada.getProperty("config.driver");
	private static final String url = ConfigJornada.getProperty("config.url");//jdbc:postgresql://
	private static final String host = ConfigJornada.getProperty("config.host");
	private static final String portNumber = ConfigJornada.getProperty("config.portNumber");
	private static final String database = ConfigJornada.getProperty("config.database");
	private static final String userName = ConfigJornada.getProperty("config.userName");
	private static final String password = ConfigJornada.getProperty("config.password")+"01";
	private static final String connectionUrl = String.format("%s%s:%s/%s", url, host, portNumber, database);
	private static final String bonecp_IdleConnectionTestPeriodInMinutes = ConfigJornada.getProperty("bonecp.IdleConnectionTestPeriodInMinutes");
	private static final String bonecp_IdleMaxAgeInMinutes = ConfigJornada.getProperty("bonecp.IdleMaxAgeInMinutes");
	private static final String bonecp_MaxConnectionsPerPartition = ConfigJornada.getProperty("bonecp.MaxConnectionsPerPartition");
	private static final String bonecp_MinConnectionsPerPartition = ConfigJornada.getProperty("bonecp.MinConnectionsPerPartition");
	private static final String bonecp_PoolAvailabilityThreshold = ConfigJornada.getProperty("bonecp.PoolAvailabilityThreshold"); 
	private static final String bonecp_PartitionCount = ConfigJornada.getProperty("bonecp.PartitionCount");
	private static final String bonecp_AcquireIncrement = ConfigJornada.getProperty("bonecp.AcquireIncrement");
	private static final String bonecp_StatementsCacheSize = ConfigJornada.getProperty("bonecp.StatementsCacheSize");
	private static final String bonecp_ReleaseHelperThreads = ConfigJornada.getProperty("bonecp.ReleaseHelperThreads");
//	private static final String bonecp_ConnectionTestStatement = ConfigJornada.getProperty("bonecp.ConnectionTestStatement");
	private static final String bonecp_LazyInit = ConfigJornada.getProperty("bonecp.LazyInit");				
	

	private static BoneCP connectionPool = null;

//	public ConnectionManager uniqueInstance;

//	public static boolean isBoneCPConnection() {
//		return boneCPConnection;
//	}

	public static void configureConnPool() {

		if(connectionPool==null){
			try {
				Class.forName(driver); // also you need the Postgre driver
				BoneCPConfig config = new BoneCPConfig();
//				config.setPoolName("paisonline");
				config.setJdbcUrl(connectionUrl);
				config.setUsername(userName);
				config.setPassword(password);				
				
				
				config.setIdleConnectionTestPeriodInMinutes(Integer.parseInt(bonecp_IdleConnectionTestPeriodInMinutes.trim()));
				config.setIdleMaxAgeInMinutes(Integer.parseInt(bonecp_IdleMaxAgeInMinutes.trim()));
				config.setMaxConnectionsPerPartition(Integer.parseInt(bonecp_MaxConnectionsPerPartition.trim()));
				config.setMinConnectionsPerPartition(Integer.parseInt(bonecp_MinConnectionsPerPartition.trim()));
				config.setPoolAvailabilityThreshold(Integer.parseInt(bonecp_PoolAvailabilityThreshold.trim())); 
				config.setPartitionCount(Integer.parseInt(bonecp_PartitionCount.trim()));
				config.setAcquireIncrement(Integer.parseInt(bonecp_AcquireIncrement.trim()));
				config.setStatementsCacheSize(Integer.parseInt(bonecp_StatementsCacheSize.trim()));
				config.setReleaseHelperThreads(Integer.parseInt(bonecp_ReleaseHelperThreads.trim()));
//				config.setConnectionTestStatement(bonecp_ConnectionTestStatement.trim());
				config.setLazyInit(Boolean.parseBoolean(bonecp_LazyInit.trim()));
				
				
				
//				config.setIdleConnectionTestPeriod(1);
//				config.setMinConnectionsPerPartition(5); // if you say 5 here, there will be 10 connection available
//				config.setMaxConnectionsPerPartition(2);
//				config.setPartitionCount(2); // 2*5 = 10 connection will be available
//				 config.setLazyInit(true); //depends on the application usage you should chose lazy or not setting Lazy true means BoneCP won't open any connections before you request a one from it.
				connectionPool = new BoneCP(config); // setup the connection pool
				System.out.println("contextInitialized.....Connection Pooling is configured");
				System.out.println("Total connections ==> "+ connectionPool.getTotalCreatedConnections());
				ConnectionManager.setConnectionPool(connectionPool);
	
			} catch (Exception e) {
				System.out.println("Erro <configureConnPool>");
				e.printStackTrace(); // you should use exception wrapping on
										// real-production code
			}
		}

	}

	public static void shutdownConnPool() {

		try {
			BoneCP connectionPool = ConnectionManager.getConnectionPool();
			System.out.println("contextDestroyed....");
			if (connectionPool != null) {
				connectionPool.shutdown(); // this method must be called only
											// once when the application stops.
				// you don't need to call it every time when you get a
				// connection from the Connection Pool
				System.out.println("contextDestroyed.....Connection Pooling shut downed!");
			}

		} catch (Exception e) {
			System.out.println("Erro <shutdownConnPool>");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {

			Connection conn = null;
			try {
				conn = getConnectionPool().getConnection();
				// will get a thread-safe connection from the BoneCP connection
				// pool.
				// synchronization of the method will be done inside BoneCP
				// source

			} catch (Exception e) {
				System.out.println("Erro <getConnection>");
				e.printStackTrace();
			}
			return conn;

		

	}

	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			System.out.println("Erro <closeStatement>");
			e.printStackTrace();
		}

	}

	public static void closeResultSet(ResultSet rSet) {
		try {
			if (rSet != null) {
				rSet.close();
			}
		} catch (Exception e) {
			System.out.println("Erro <closeResultSet>");
			e.printStackTrace();
		}

	}

	public static void closeConnection(Connection conn) {
		
			try {
				if (conn != null) {
					conn.close(); // release the connection - the name is tricky
									// but
									// connection is not closed it is released
				} // and it will stay in pool
			} catch (SQLException e) {
				System.out.println("Erro <closeConnection>");
				e.printStackTrace();
			}


	}

	public static BoneCP getConnectionPool() {
		return connectionPool;
	}

	public static void setConnectionPool(BoneCP connectionPool) {
		ConnectionManager.connectionPool = connectionPool;
	}

}
