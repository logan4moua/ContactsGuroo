package com.ics425.models.user;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ics425.config.Config;
import com.ics425.miscTools.PasswordUtil;
import com.ics425.miscTools.StringToMessageDigestConverter;

@SuppressWarnings("serial")
public class UserLoginInfo implements Serializable{
	
	private String username;
	private String password;
	
	public UserLoginInfo(){
		
	}
	
	public UserLoginInfo(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	
	public void persist(int u_id, Connection conn){
		PreparedStatement pStatement = null;

		try {
			// persist user login info
			pStatement = (PreparedStatement) conn.prepareStatement(Config.loginInfoInsert);
			pStatement.setInt(1, u_id);
			pStatement.setString(2, username);
			pStatement.setString(3, StringToMessageDigestConverter.convert(password));
			pStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param u_id
	 * @param conn
	 */
	public void update(int u_id, Connection conn){

		PreparedStatement pStatement = null;
		
		try {
			pStatement = (PreparedStatement) conn.prepareStatement(Config.u_userNameUpdate);
			pStatement.setString(1, username);
			pStatement.setInt(2, u_id);

			pStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param u_id
	 * @param old_pass
	 * @param new_pass
	 * @return
	 */
	public boolean changePassword(int u_id, String old_pass, String new_pass){
		if(old_pass.compareTo(password)==0){
			
			return PasswordUtil.updatePassword(u_id, new_pass);
		}
		
		return false;
	}

}
