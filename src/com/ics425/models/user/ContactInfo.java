package com.ics425.models.user;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ics425.config.Config;

@SuppressWarnings("serial")
public class ContactInfo implements Serializable{
	
	private String phone;
	private String email;
	
	public ContactInfo(){
		this.phone = "";
		this.email = "";
	}
	
	public ContactInfo(String phone, String email) {
		super();
		this.phone = formatPhoneNumber(phone);
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = formatPhoneNumber(phone);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString(){
		String str = new String();
		str += phone + "<br>";
		str += email + "<br>";
		
		return str;
	}
	
	public String formatPhoneNumber(String phone){
		
		if(phone.length() == 12)
				return phone;
		
		String str = new String();
		int count = 0;
		for(char ch: phone.toCharArray()){
			if(count == 3 | count == 6){
				str += "-"+ch;
			}else
				str += ch;
			count++;
			
		}
		return str;
	}
	
	/**
	 * 
	 * @param u_id
	 * @param conn
	 */
	public void update(int u_id, Connection conn){

		PreparedStatement pStatement = null;
		
		try {
			pStatement = (PreparedStatement) conn.prepareStatement(Config.u_contactInfoUpdate);
			pStatement.setString(1, phone);
			pStatement.setString(2, email);
			pStatement.setInt(3, u_id);

			pStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param u_id int user id
	 * @param conn Connection
	 */
	public void persist(int u_id, Connection conn){
		PreparedStatement pStatement = null;
	
		try {
			// persist contact information
			pStatement = (PreparedStatement) conn.prepareStatement(Config.contactInsert);
			pStatement.setInt(1, u_id);
			pStatement.setString(2, phone);
			pStatement.setString(3, email);
			pStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean emailDuplicated(String email) {
		
		
		
		return false;
	}
}
