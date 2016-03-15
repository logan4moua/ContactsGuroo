package com.ics425.models.user;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ics425.config.Config;

@SuppressWarnings("serial")
public class Address implements Serializable {
	
	private String street;
	private String city;
	private String state;
	private String zip;
	
	public Address(){
		this.street = "";
		this.city = "";
		this.state = "";
		this.zip = "";
	}
	
	public Address(String street, String city, String state, String zip) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String toString(){
		String str = new String();
		str += street + ", ";
		str += city + ", ";
		str += state + " ";
		str += zip ;
		
		return str;
	}
	
	/**
	 * persist address for new user
	 * @param u_id int 
	 * @param conn Connection
	 */
	public void persist(int id, Connection conn){
		persist(id, conn, false);
	}
	
	/**
	 * persist new address for contact
	 * @param c_id int
	 * @param conn Connection
	 * @param isContacts boolean
	 */
	public void persist(int id, Connection conn, boolean isContacts){
		String sql = Config.addressInsert;
		if(isContacts){
			sql = Config.c_addressInsert;
		}
		PreparedStatement pStatement = null;
		
		// persist address
		try {
			pStatement = (PreparedStatement) conn.prepareStatement(sql);
			pStatement.setInt(1, id);
			pStatement.setString(2, street);
			pStatement.setString(3, city);
			pStatement.setString(4, state);
			if(zip == "")
				zip = "-1";
			pStatement.setInt(5, Integer.parseInt(zip));
			pStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * update user's address table
	 * @param u_id
	 * @param conn
	 */
	public void update(int u_id, Connection conn){
		update(u_id, conn, false);
	}
	
	/**
	 * update contact's address table
	 * @param id
	 * @param conn
	 * @param isContacts
	 */
	public void update(int id, Connection conn, boolean isContacts){
		String sql = Config.u_addressUpdate;
		if(isContacts){
			sql = Config.c_addressUpdate;
		}
		PreparedStatement pStatement = null;
		
		try {
			pStatement = (PreparedStatement) conn.prepareStatement(sql);
			pStatement.setString(1, street);
			pStatement.setString(2, city);
			pStatement.setString(3, state);
			pStatement.setString(4, zip);
			pStatement.setInt(5, id);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
