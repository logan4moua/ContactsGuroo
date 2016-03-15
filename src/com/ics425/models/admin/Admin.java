package com.ics425.models.admin;

import java.io.Serializable;
import java.sql.*;

import org.apache.tomcat.util.security.MD5Encoder;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;
import com.ics425.miscTools.StringToMessageDigestConverter;

@SuppressWarnings("serial")
public class Admin implements Serializable{

	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean master;
	private int a_id;
	
	private Connection conn = null;
	
	public Admin(String u, String p){
		firstName = "";
		lastName = "";
		username = u;
		password = p;
		setA_id(0);
		setMaster(false);
	}
	
	public Admin() {
	}
	/**
	 * for creating none master admin
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param password
	 */
	public Admin(String firstName, String lastName, String username, String password) {
		this(firstName, lastName, username, password, false);
	}
	
	/**
	 * for creating master admin
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param password
	 * @param master
	 */
	public Admin(String firstName, String lastName, String username, String password, boolean master) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		setMaster(master);
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * remove this admin
	 * @return
	 */
	public boolean remove(){
		conn = Config.getConnection();
		PreparedStatement pStatement = null;
		
		try {
			pStatement = conn.prepareStatement(Config.adminAccountDelete);
			pStatement.setInt(1, a_id);
			pStatement.executeUpdate();

			return true;
		} catch (SQLException e) {
			if(conn != null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}finally{
			try {
				if(null != pStatement)pStatement.close();
				if(null != conn) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Persists an admin
	 */
	public void persist(){
		conn = Config.getConnection();

		PreparedStatement pStatement = null;
		Statement statement = null;
		ResultSet result = null;

		try {			
			
			// persist names
			pStatement = (PreparedStatement) conn.prepareStatement(Config.adminInsert);
			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);
			pStatement.setString(3, username);
			pStatement.setString(4, StringToMessageDigestConverter.convert(password));
			pStatement.setBoolean(5, master);
			pStatement.executeUpdate();

			
		} catch (SQLException e) {
			
			if(null != conn){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}finally{
				try {
					if(null != pStatement)pStatement.close();
					if(null != conn) conn.close();
					if(null != statement) statement.close();
					if(null != result) result.close();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}

	}
	
	/**
	 * Authenticating admin
	 * @return
	 */
	public boolean isAuthenticated(){
		conn = Config.getConnection();

		PreparedStatement pStatement = null;
		ResultSet result = null;
		
		try {
			pStatement = conn.prepareStatement(Config.getAdminQuery);
			pStatement.setString(1, username);
			pStatement.setString(2, StringToMessageDigestConverter.convert(password, Config.mdAlgorithm));
			result = pStatement.executeQuery();
			
			if(result.next()){
				this.firstName = result.getString("first_name");
				this.lastName = result.getString("last_name");
				this.setA_id(result.getInt("a_id"));
				this.master = result.getBoolean("master");
				
				return true;
			}
			
			
		} catch (SQLException e) {
			if(conn != null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally{
				try {
					if(null != pStatement)pStatement.close();
					if(null != conn) conn.close();
					if(null != result) result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return false;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public int getA_id() {
		return a_id;
	}

	public void setA_id(int a_id) {
		this.a_id = a_id;
	}
	
	

}
