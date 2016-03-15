package com.ics425.miscTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ics425.config.Config;

public class UsernameRetreiver {
	private static Connection conn = null;
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public static String retreiveUsername(String email){
		conn = Config.getConnection();
		PreparedStatement pStatement = null;
		ResultSet result = null;
		
		try {
			pStatement = conn.prepareStatement(Config.getUsernameByEmail);
			pStatement.setString(1, email);
			result = pStatement.executeQuery();
			
			if(result.next()){
				return result.getString("username");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				try {
					if(conn != null)conn.close();
					if(result != null)result.close();
					if(pStatement != null)pStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return "";
	}

}
