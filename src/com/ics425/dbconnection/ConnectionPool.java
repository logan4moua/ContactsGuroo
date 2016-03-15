package com.ics425.dbconnection;

import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ics425.config.Config;

import java.sql.Connection;

public class ConnectionPool {
	
	private static ConnectionPool pool = null;
	private static DataSource ds = null;
	
	private ConnectionPool(){
		try {
			
			InitialContext ctx = new InitialContext();
			ds = (DataSource)ctx.lookup(Config.resourcesLookup);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized ConnectionPool getInstance(){
		if(pool == null){
			pool = new ConnectionPool();
		}		
		return pool;
	}
	
	public Connection getConnection(){
		try {
			return (Connection) ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void freeConnection(Connection c){
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
