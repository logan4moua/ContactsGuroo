package com.ics425.models.user;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;

@SuppressWarnings("serial")
public class UserCollection implements Serializable{
	
	private static Connection conn = null;	
	private ArrayList<User> userList;
	
	public UserCollection(){
		userList = new ArrayList<User>();
	}
	
	public synchronized void add(User user){
		userList.add(user);
	}

	public synchronized ArrayList<User> getUserList() {
		return userList;
	}

	public synchronized void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}
	
	public synchronized int getCount(){
		return userList.size();
	}
	
	/**
	 * remove a user from database and this list
	 * @param u_id
	 * @return
	 */
	public boolean remove(int u_id){
		for(User u: userList){
			if(u_id == u.getU_id()){
				return u.remove() && userList.remove(u);
			}
		}
		return false;
	}

	
	/**
	 * ArrayList<User> getAllUsers()
	 * @return user list
	 */
	public synchronized  static UserCollection queryAllUsers(){
		conn = Config.getConnection();
		Statement statement = null;
		ResultSet result = null;
		
		UserCollection uc = new UserCollection();
		
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(Config.allUsersQuery);

			while(result.next()){
				User user = new User();
				user.setFirstName(result.getString("first_name"));
				user.setLastName(result.getString("last_name"));
				user.setU_id(result.getInt("u_id"));

				UserLoginInfo uli = new UserLoginInfo();
				uli.setUsername(result.getString("username"));
				user.setUserLoginInfo(uli);
				
				uc.add(user);
				
			}
			
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

		return uc;
	}
	
	

}
