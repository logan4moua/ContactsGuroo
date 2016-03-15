package com.ics425.miscTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;

public class PasswordUtil {
	private static Connection conn = null;
	
	public PasswordUtil(){}
	
	public static boolean isValidPassword(String password){
		
//		(/^
//				(?=.*\d)                //should contain at least one digit
//				(?=.*[a-z])             //should contain at least one lower case
//				(?=.*[A-Z])             //should contain at least one upper case
//				[a-zA-Z0-9]{8,}         //should contain at least 8 from the mentioned characters
//				$/)
		
		if (password.length() >= 8 && password.length() <= 24){
			
			if (password.matches("[a-zA-Z0-9!@#$%^&*()]+")&&
				password.matches("^.*(?=[a-z]){2,}.*$")&&
				password.matches("^.*(?=[A-Z]){2,}.*$")&&
				password.matches("^.*(?=[0-9]){2,}.*$")&&
				password.matches("^.*(?=[!@#$%^&*()]){2,}.*$"))
					return true;
				
		}
		
		return false;
	}
	
	/**
	 * create random password 
	 * @return random password
	 */
	public static String createRandomPassword(){
		StringBuilder string = new StringBuilder();

		char []typeList = {'a', 'A', '0'};
		char []spcList = {'!','@','#','$','%','^','&','*','(',')'};
		
		int len = 6;
		String [] stringResource = new String[len];

		Random rand = new Random();
		for(int i=0; i<len; i++){
			if(i<3)
				stringResource[i] = createSubPassword(typeList[i]);
			else
				stringResource[i] = String.valueOf(createSpcSubPassword(spcList));
		}
		Integer [] randHistory = new Integer[len];
		for(int i=0; i<len; i++){
			int randNum;

			do{
				randNum = rand.nextInt(len);

			}while(isDuplicated(randNum, randHistory));
			
			randHistory[i] = randNum;
			string.append(stringResource[randNum]);
		}

		return string.toString();
	}
	/**
	 * 
	 * @param number
	 * @param list
	 * @return
	 */
	private static boolean isDuplicated(int number, Integer[] list){
		for(int i=0; i<list.length; i++){
			if(list[i] != null){
				if(number == list[i]){
					return true;
				}			
			}
		}
		return false;		
	}
	/**
	 * 
	 * @param spcList special characters list 
	 * @return
	 */
	private static char createSpcSubPassword(char[] spcList){

		return spcList[new Random().nextInt(spcList.length)];
	}
	/**
	 * 
	 * @param startChar starting character of those sequential characters
	 * @return
	 */
	private static String createSubPassword(char startChar){
		int count = 0;
		Random rand = new Random();
		int range = 26;
		String str = "";
		
		do{
			if(startChar == '0')range = 10;		
			char ch = (char) ((int)startChar + rand.nextInt(range));			
			str += ch;
			count++;
			
		}while(count < 2);
		
		return str;
		
	}
	
	
	private static void establishConnection(){
		ConnectionPool pool = ConnectionPool.getInstance();
		conn = pool.getConnection();
	}
	
	/**
	 * 
	 * @param u_id
	 * @param newPassword
	 * @return
	 */
	public static boolean updatePassword(int u_id, String newPassword){
		establishConnection();
		boolean done = updatePassword(u_id, newPassword, conn);
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}
	/**
	 * 
	 * @param u_id
	 * @param newPassword
	 * @param Conneciton
	 * @return
	 */
	public static boolean updatePassword(int u_id, String newPassword, Connection conn){
		PreparedStatement pStatement = null;
		
		String passwordHex = StringToMessageDigestConverter.convert(newPassword);
		
		try {
			pStatement = conn.prepareStatement(Config.u_passwordUpdate);
			pStatement.setString(1, passwordHex);
			pStatement.setInt(2, u_id);
			pStatement.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			if(conn != null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			try {
				if(pStatement != null) pStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
		return false;
	}
	
	/**
	 * 
	 * @param email the user email account
	 * @return tempolary password
	 */
	public static String assignTempPassword(String email){
		establishConnection();
		String password = "";
		
		PreparedStatement pStatement = null;
		ResultSet result = null;
		
		try {
			conn.setAutoCommit(false);
			
			pStatement = conn.prepareStatement(Config.getUIDQuery);
			pStatement.setString(1, email);
			result = pStatement.executeQuery();
			
			if(result.next()){
				int u_id = result.getInt(1);
				
				password = createRandomPassword();
				updatePassword(u_id, password, conn);			
			}
			
			conn.commit();			
			return password;
			
			
		} catch (SQLException e) {
			if(conn != null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}finally{
				try {
					if(conn != null)conn.close();
					if(result != null) result.close();
					if(pStatement != null) pStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return null;
	}

}
