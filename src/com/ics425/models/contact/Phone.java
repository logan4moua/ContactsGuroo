package com.ics425.models.contact;

import java.io.Serializable;
import java.sql.*;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;

public class Phone implements Serializable{
	private String phone;
	private int c_id;
	private int p_id;
	
	public Phone(String ph, int c_id, int p_id){
		phone = formatPhoneNumber(ph);
		this.c_id = c_id;
		this.p_id = p_id;
	}
	
	public Phone(String ph){
		this(ph, 0, 0);
	}
	
	/**
	 * 
	 * @param conn Connection
	 */
	public void update(Connection conn){
		PreparedStatement pStatement = null;
		try {
			pStatement = (PreparedStatement)conn.prepareStatement(Config.c_phoneUpdate);
			pStatement.setString(1, phone);
			pStatement.setInt(2, p_id);
			pStatement.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param c_id int
	 * @param conn Connection
	 */
	public void persist(int c_id, Connection conn){
		PreparedStatement pStatement = null;
		try {
			pStatement = (PreparedStatement) conn.prepareStatement(Config.c_phoneInsert);
			pStatement.setInt(1, c_id);
			pStatement.setString(2, phone);
			pStatement.executeUpdate();	
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * TODO
	 * @return
	 */
	public boolean remove(){
		return false;
	}

	
	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public void setPhone(String phone){
		this.phone = formatPhoneNumber(phone);
	}
	
	public String getPhone(){
		return phone;
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


}
