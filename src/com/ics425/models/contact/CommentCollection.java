package com.ics425.models.contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;

public class CommentCollection {
	private ArrayList<Comment> commentList;
	
	public CommentCollection(){
		commentList = new ArrayList<Comment>();
	}
	
	/**
	 * 
	 * @param comment
	 * @param c_id
	 * @param p_id
	 */
	public void add(String comment, int c_id, int p_id){
		commentList.add(new Comment(comment, c_id, p_id));
	}
	/**
	 * 
	 * @param comment
	 */
	public void add(Comment comment){
		commentList.add(comment);
	}
	
	public void update(Connection conn){
		for(Comment cm: commentList){
			cm.update(conn);
		}
	}
	
	public void persist(int c_id, Connection conn){
		for(Comment cm: commentList){
			cm.persist(c_id, conn);
		}
	}
	
	public Comment get(int index){
		return commentList.get(index);
	}

	public ArrayList<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(ArrayList<Comment> commentList) {
		this.commentList = commentList;
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		for(Comment cm: commentList){
			str.append(cm.getComment()+"<br>");
		}
		return str.toString();
	}
	/**
	 * 
	 * @param c_id
	 * @return
	 */
	public static CommentCollection queryMyComments(int c_id, Connection conn){
		CommentCollection cc = new CommentCollection();
		
		PreparedStatement pStatement = null;
		ResultSet result = null;
		
		try {
			pStatement = (PreparedStatement) conn.prepareStatement(Config.allCommentsQuery);
			pStatement.setInt(1, c_id);
			result = pStatement.executeQuery();
			
			while(result.next()){
				cc.add(new Comment(result.getString("comment"), result.getInt("c_id"), result.getInt("cm_id")));
			}
			
			return cc;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
