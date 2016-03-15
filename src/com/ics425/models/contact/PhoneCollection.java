package com.ics425.models.contact;

import java.sql.*;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;

public class PhoneCollection {
	private Phone[] phoneList;
	private int count;

	public PhoneCollection(){
		phoneList = new Phone[2];
		count = 0;
	}
	
	public void persist(int c_id, Connection conn){
		for(Phone ph: phoneList){
			ph.persist(c_id, conn);
		}
	}
	
	public void update(Connection conn){
		for(Phone ph: phoneList){
			ph.update(conn);
		}
	}
	/**
	 * 
	 * @param c_id
	 * @return
	 */
	public static PhoneCollection queryMyPhones(int c_id, Connection conn){
		PhoneCollection pc = new PhoneCollection();
		
		PreparedStatement pStatement = null;
		ResultSet result = null;
		
		try {
			pStatement = (PreparedStatement) conn.prepareStatement(Config.allPhonesQuery);
			pStatement.setInt(1, c_id);
			result = pStatement.executeQuery();
			
			while(result.next()){
				pc.add(new Phone(result.getString("phone"), result.getInt("c_id"), result.getInt("p_id")));
			}
			
			return pc;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void add(Phone phone){
		phoneList[count++] = phone;
	}
	
	public boolean remove(int p_id){
		if(count > 1){
			
			for(int i=0; i<count; i++){
				if(phoneList[i].getP_id() == p_id){
					phoneList[i].remove();
					phoneList[i] = null;
					count--;
					
					if(phoneList[0] == null){
						phoneList[0] = phoneList[1];
						phoneList[1] = null;
					}
					
					return true;
				}
			}
		}
		return false;
	}
	public Phone[] getPhoneList() {
		return phoneList;
	}
	
	public Phone get(int index){
		if(index < count && index > -1)
			return phoneList[index];
		return null;
	}
	
	public void setPhoneList(Phone[] phoneList) {
		this.phoneList = phoneList;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		for(Phone p: phoneList){
			str.append(p.getPhone()+ "<br>");
		}
		return str.toString();
	}
}
