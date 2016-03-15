package com.ics425.models.user;

import java.io.Serializable;
import java.sql.*;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;
import com.ics425.miscTools.StringToMessageDigestConverter;

@SuppressWarnings("serial")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private String firstName;
	private String lastName;
	private Address address;
	private ContactInfo contactInfo;
	private UserLoginInfo userLoginInfo;
	private Date dateJoined;
	private int u_id;
	
	private static Connection conn = null;
	
	public User(){
		this.firstName = "";
		this.lastName = "";
		this.address = new Address();
		this.contactInfo = new ContactInfo();
		this.userLoginInfo = new UserLoginInfo();
		this.dateJoined = null;
		u_id = 0;
		
	}

	public User(String firstName, String lastName, Address address, ContactInfo contactInfo,
			UserLoginInfo userLoginInfo) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.contactInfo = contactInfo;
		this.userLoginInfo = userLoginInfo;
	}
	
	public int getU_id() {
		return u_id;
	}
	
	public void setU_id(int u_id) {
		this.u_id = u_id;
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public ContactInfo getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	public UserLoginInfo getUserLoginInfo() {
		return userLoginInfo;
	}
	public void setUserLoginInfo(UserLoginInfo userLoginInfo) {
		this.userLoginInfo = userLoginInfo;
	}
	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}
	public String toString(){
		String str = new String();
		str += firstName + "<br>";
		str += lastName + "<br>";
		str += address.toString();
		str += contactInfo.toString();
		
		return str;
	}

	
	public boolean remove(){
		conn = Config.getConnection();
		PreparedStatement pStatement = null;
		
		try {
			conn.setAutoCommit(false);
			pStatement = conn.prepareStatement(Config.userContactsDelete);
			pStatement.setInt(1, u_id);
			pStatement.executeUpdate();
			
			pStatement = conn.prepareStatement(Config.userAccountDelete);
			pStatement.setInt(1, u_id);
			pStatement.executeUpdate();
			
			conn.commit();
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
	
	public void update(){
		conn = Config.getConnection();
		
		PreparedStatement pStatement = null;
		
		try {
			conn.setAutoCommit(false);
			pStatement = conn.prepareStatement(Config.userUpdate);
			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);
			pStatement.setInt(3, u_id);
			pStatement.executeUpdate();
			
			address.update(u_id, conn);
			contactInfo.update(u_id, conn);
			userLoginInfo.update(u_id, conn);
			
			conn.commit();
			
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
	
	}
	
	/**
	 * 
	 * @return
	 */
	public User queryMyProfile(){
		conn = Config.getConnection();
		
		PreparedStatement pStatement = null;
		ResultSet result = null;
		
		try {
			pStatement  = conn.prepareStatement(Config.getFullUserQuery);
			pStatement.setInt(1, u_id);
			result = pStatement.executeQuery();
			
			if(result.next()){
				setFirstName(result.getString("first_name"));
				setLastName(result.getString("last_name"));
				setU_id(u_id);
				
				setAddress(new Address(result.getString("street"), result.getString("city"), 
						result.getString("state"), String.valueOf(result.getInt("zip"))));
				setContactInfo(new ContactInfo(result.getString("phone"), result.getString("email")));
				setUserLoginInfo(new UserLoginInfo(result.getString("username"), result.getString("password")));
				
				return this;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			try {
				if(null != pStatement)pStatement.close();
				if(null != conn) conn.close();
				if(null != result) result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * User to validate a user login
	 * @return true if use is authenticated, otherwise, false.
	 */
	public boolean isAuthenticated(){
		conn = Config.getConnection();

		PreparedStatement pStatement = null;
		ResultSet result = null;
		
		try {
			pStatement = conn.prepareStatement(Config.getUserQuery);
			pStatement.setString(1, userLoginInfo.getUsername());
			pStatement.setString(2, StringToMessageDigestConverter.convert(userLoginInfo.getPassword()));
			result = pStatement.executeQuery();
			
			if(result.next()){
				setU_id(result.getInt("u_id"));
				setFirstName(result.getString("first_name"));
				setLastName(result.getString("last_name"));
				this.contactInfo.setEmail(result.getString("email"));
				
				return true;
			}
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	
	/**
	 * persists this user to database
	 */
	@SuppressWarnings("resource")
	public synchronized void persist(){
		conn = Config.getConnection();

		PreparedStatement pStatement = null;
		Statement statement = null;
		ResultSet result = null;

		try {
			conn.setAutoCommit(false); 			
			
			// persist names
			pStatement = (PreparedStatement) conn.prepareStatement(Config.nameInsert);
			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);
			pStatement.executeUpdate();
			
			statement = (Statement) conn.createStatement();
			result = statement.executeQuery("SELECT LAST_INSERT_ID()");

			if(result.next()){
				u_id = result.getInt(1);
				
				address.persist(u_id, conn);
				contactInfo.persist(u_id, conn);
				userLoginInfo.persist(u_id, conn);				

			}
			else
				throw new IllegalStateException("Unable to retreive last id");
			
			conn.commit();
			
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
	

}
