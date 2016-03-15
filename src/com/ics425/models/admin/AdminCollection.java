package com.ics425.models.admin;

import java.sql.*;
import java.util.ArrayList;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;
import com.ics425.models.user.User;

public class AdminCollection {
	ArrayList<Admin> adminList;
	
	private static Connection conn = null;
	
	public AdminCollection(){
		adminList = new ArrayList<Admin>();
	}
	
	public synchronized ArrayList<Admin> getAdminList(){
		return adminList;
	}
	
	public synchronized void add(Admin admin){
		adminList.add(admin);
	}

	/**
	 * remove an admin from database and this list
	 * @param a_id admin id
	 * @return
	 */
	public boolean remove(int a_id){
		for(Admin a: adminList){
			if(a_id == a.getA_id()){
				return a.remove() && adminList.remove(a);
			}
		}
		return false;
	}
	/**
	 * get all admins from this list
	 * @return
	 */
	public static synchronized AdminCollection queryAllAdmins(){
		conn = Config.getConnection();
		
		Statement statement = null;
		ResultSet result = null;
		
		AdminCollection ac = new AdminCollection();
		
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(Config.allAdminsQuery);

			while(result.next()){
				Admin admin = new Admin();
				admin.setFirstName(result.getString("first_name"));
				admin.setLastName(result.getString("last_name"));
				admin.setUsername(result.getString("username"));
				admin.setA_id(result.getInt("a_id"));
				
				ac.add(admin);
				
			}
			
			return ac;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
				try {
					if(null != conn) conn.close();
					if(null != statement) statement.close();
					if(null != result) result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return null;
	}

}
