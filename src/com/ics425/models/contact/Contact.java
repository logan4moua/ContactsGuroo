package com.ics425.models.contact;

import java.io.Serializable;
import java.sql.*;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;
import com.ics425.models.user.*;

@SuppressWarnings("serial")
public class Contact implements Serializable{
	private String firstName;
	private String lastName;
	private Address address;
	private PhoneCollection phones;
	private CommentCollection comments;
	private int c_id;
	private int u_id;	

	private Connection conn;

	public Contact(){
	}
	public Contact(int u_id, String firstName2, String lastName2, Address address2, PhoneCollection phones2) {
		this.firstName = firstName2;
		this.lastName = lastName2;
		this.address = address2;
		this.phones = phones2;
		this.u_id = u_id;
	}
	/**
	 * get user's id
	 * @return
	 */
	public int getU_id() {
		return u_id;
	}
	
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	
	/**
	 * get this contact's id
	 * @return
	 */
	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
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
	
	public PhoneCollection getPhones() {
		return phones;
	}
	public void setPhones(PhoneCollection phones) {
		this.phones = phones;
	}
	public CommentCollection getComments() {
		return comments;
	}
	public void setComments(CommentCollection comments) {
		this.comments = comments;
	}
	
	
	public void update(){
		conn = Config.getConnection();
		
		PreparedStatement pStatement = null;
		
		try {
			conn.setAutoCommit(false);
			pStatement = (PreparedStatement) conn.prepareStatement(Config.c_nameUpdate);
			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);
			pStatement.setInt(3, c_id);
			pStatement.executeUpdate();

			address.update(c_id, conn, true);			
			phones.update(conn);
			comments.update(conn);
			
			conn.commit();

		} catch (SQLException e) {
			if(conn != null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				try {
					if(null != pStatement)pStatement.close();
					if(null != conn)conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	/**
	 * remove self from database
	 * @return
	 */
	public boolean remove(){
		conn = Config.getConnection();
		
		PreparedStatement pStatement = null;
		
		try {			
			
			// persist names
			pStatement = (PreparedStatement) conn.prepareStatement(Config.removeContact);
			pStatement.setInt(1, c_id);
			pStatement.executeUpdate();
			
			return true;
			
			
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
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		return false;
	}
	
	/**
	 * persists this contact to database
	 */
	@SuppressWarnings("resource")
	public void persist(){
		conn = Config.getConnection();

		PreparedStatement pStatement = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			conn.setAutoCommit(false); 			
			
			// persist names
			pStatement = (PreparedStatement) conn.prepareStatement(Config.c_nameInsert);
			pStatement.setInt(1, u_id);
			pStatement.setString(2, firstName);
			pStatement.setString(3, lastName);
			pStatement.executeUpdate();
			
			statement = (Statement) conn.createStatement();
			result = statement.executeQuery("SELECT LAST_INSERT_ID()");
			
			if(result.next()){
				c_id = result.getInt(1);
				
				address.persist(c_id, conn, true);
				
				phones.persist(c_id, conn);

				comments.persist(c_id, conn);

				
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
