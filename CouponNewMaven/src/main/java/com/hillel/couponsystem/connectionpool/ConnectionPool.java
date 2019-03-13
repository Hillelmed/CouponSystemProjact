
package com.hillel.couponsystem.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;

/**
 * Connection Pool gives a Connection to the DB-SingleTone
 * 
 * @author HillelMed
 */
public class ConnectionPool {

	private Stack<Connection> connections = new Stack<>();
	private static ConnectionPool instance = new ConnectionPool();
	private String username = "root";
	private String password = "1234";
	private static final String connectionString = "jdbc:mysql://localhost:3306/couponjpa?serverTimezone=UTC";
	private final int MAX = 10;
	private boolean ifcangetConnection = true;

	/**
	 * Private CTor for ConnectionPool
	 * 
	 * @author HillelMed
	 */
	private ConnectionPool() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		for (int i = 1; i <= MAX; i++) {
			try {
				Connection conn = DriverManager.getConnection(connectionString, username, password);
				connections.push(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return instance of Connection pool Singeltron
	 */
	public static ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	/**
	 * give The user a connection to the DB
	 * 
	 * @return Connection for DB
	 * @throws CouponSystemExeption
	 * @throws InterruptedException
	 */
	public Connection getConnection() throws CouponSystemExeption {
		if (ifcangetConnection) {

			synchronized (connections) {
				while (connections.isEmpty()) {
					try {
						connections.wait();
					} catch (InterruptedException e) {
						throw new CouponSystemExeption(e.getMessage());
					}
				}

				Connection c = connections.pop();
				return c;
			}
		}
		return null;
	}

	/**
	 * when the user finishes with the Connection, this Function takes it back
	 * 
	 * @param connection
	 * @throws SystemConnectionRestore
	 */
	public void restoreConnection(Connection connection) throws CouponSystemExeption {

		if (connection != null) {
			synchronized (connections) {
				connections.push(connection);
				connections.notify();
			}
		} else {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_CPOOL);
		}
	}

	/*
	 * closes all the connection for the DB
	 * 
	 * @throws SQLException
	 * 
	 * @throws InterruptedException
	 */
	public void closeAllConnections() throws CouponSystemExeption, SQLException {
		ifcangetConnection = false;
		synchronized (connections) {
			while (connections.size() < MAX) {
				try {
					connections.wait();
				} catch (InterruptedException e) {

				}
			}
			for (Connection conn : connections) {
				conn.close();
			}
		}
	}

}