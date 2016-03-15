package com.ics425.models.contact;

import java.io.Serializable;
import java.sql.*;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;

public class Comment implements Serializable{
	private String comment; 
	private int c_id;
	private int cm_id;
	
	public Comment(String comment, int c_id, int cm_id){
		this.comment = comment;
		this.c_id = c_id;
		this.cm_id = cm_id;
	}
	
	public Comment(String comment){
		this(comment, 0, 0);
	}

	
	/**
	 * TODO
	 * @return
	 */
	public void update(Connection conn){
		PreparedStatement pStatement = null;
		try {
			pStatement = (PreparedStatement)conn.prepareStatement(Config.c_commentUpdate);
			pStatement.setString(1, comment);
			pStatement.setInt(2, cm_id);
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
			pStatement = (PreparedStatement) conn.prepareStatement(Config.c_commentInsert);
			pStatement.setInt(1, c_id);
			pStatement.setString(2, comment);
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	public int getCm_id() {
		return cm_id;
	}

	public void setCm_id(int cm_id) {
		this.cm_id = cm_id;
	}
	
	

}
