package com.ics425.config;

import java.sql.*;
import java.util.ArrayList;

import com.ics425.dbconnection.ConnectionPool;

public class Config {
	// website title
	public static final String TITLE = "CONTACTS GUROO";
	
	
	// regex
	public static final String SCRIPT_REGEX = "<script\b[^<]*(?:(?!</script>)<[^<]*)*</script>/";
	
	// action buttons
	public static final String PREVIOUS = "PREVIOUS";
	public static final String NEXT = "NEXT";
	public static final String SUBMIT = "SUBMIT";
	public static final String FINALIZE = "FINALIZE";
	public static final String CANCEL = "CANCEL";
	public static final String CONFIRM = "CONFIRM";
	public static final String RETURN = "RETURN";
	public static final String SEARCH ="SEARCH";
	public static final String COMMIT = "COMMIT";
	public static final String DELETE = "DELETE";
	public static final String SEND = "SEND";
	public static final String SIGN_IN = "SIGN IN";
	
	// Error messages
	public static final String errorMsg = "errorMsg";
	public static final String passwordNotMatchedMessage = "Passwords not matched! Please, re-enter passwords";
	public static final String passwordMissedCriteriaMessage = "Passwords not meet criteria! Please, re-enter passwords";
	public static final String emptyNameFieldError = "Name(s) cannot be empty";
	public static final String signinErrorMsg = "Sign-in error";
	public static final String removingUserFailedMsg = "Trying to remove user failed";
	public static final String removingAdminFailedMsg = "Trying to remove admin failed";
	public static final String emptyFieldErrorMsg = "Cannot leave any field blank";
	public static final String emailDuplicatedErrorMsg = "The email is already in use";
	public static final String backupDBFailedMsg = "Attempting to backup database has failed";
	
	// Success messages
	public static final String successMsg = "successMsg";
	public static final String contactAddedSuccessMsg = "Contact has been successfully added";
	public static final String contactUpdatedSuccessMsg = "Contact has been successfully updated";
	public static final String contactDeletedSuccessMsg = "Contact has been successfully deleted";
	public static final String loggedOutSuccessMsg = "You have successcully logged out";
	public static final String mailSentSuccessMsg = "Thank you fo cantacting us. Your feedback is very important to us.";
	public static final String passwordSentToEmailMsg = "Tempolary password was successfully sent to your email, Thank you.";
	public static final String passwordChangedSuccessMsg = "Password has been changed successfully";
	public static final String passwordChangedErrorMsg = "Error trying to change password!";
	public static final String addedNewUserSuccessMsg = "New user has been added successfully";
	public static final String removedUserSuccessMsg = "User has been removed successfully";
	public static final String removedAdminSuccessMsg = "Admin has been removed successfully";
	public static final String usernameSentSuccessMsg = "Your username was sent successfully";
	public static final String profileUpdatedSuccessMsg = "Your profile has been updated successfully";
	public static final String backupDBSuccessMsg = "Backup database has been success";
	
	/**
	 * Directories
	 */
	// Home page
	public static final String HOME = "/index.jsp";
	
	//User registration page names
	public static final String mainRegDir = "/Registration";
	public static final String getNamePage = mainRegDir+"/name.jsp";
	public static final String getAddressContactPage = mainRegDir+"/address_contact.jsp";
	public static final String getUserLoginInfoPage = mainRegDir+"/user_login_info.jsp";
	public static final String confirmationPage = mainRegDir+"/confirmation.jsp";
	
	//Contact Management page names
	public static final String mainUserDir = "/User";
	public static final String userLoginPage = mainUserDir+"/user_login.jsp";
	public static final String manageContactsPage = mainUserDir+"/manage_contacts.jsp";
	public static final String viewContactsPage = mainUserDir+"/view_contacts.jsp";
	public static final String viewContactDetailsPage = mainUserDir+"/view_contact_details.jsp";
	public static final String addNewContactPage = mainUserDir+"/add_new_contact.jsp";
	public static final String addCommentsPage = mainUserDir+"/add_comments.jsp";
	public static final String editContactPage = mainUserDir+"/edit_contact.jsp";
	public static final String searchResultPage = mainUserDir+"/search_result.jsp";
	public static final String searchContactPage = mainUserDir+"/search_contact.jsp";
	public static final String viewMyProfilePage = mainUserDir+"/view_my_profile.jsp";
	public static final String changePasswordPage = mainUserDir+"/change_password.jsp";
	

	// Admin pages
	public static final String mainAdminDir = "/Administrator";
	public static final String adminLoginPage = mainAdminDir+"/admin_login.jsp";
	public static final String viewAllUsersPage = mainAdminDir+"/view_all_users.jsp";
	public static final String viewAllAdminsPage = mainAdminDir+"/view_all_admins.jsp";
	public static final String addNewUserPage = mainAdminDir+"/add_new_user.jsp";
	public static final String addNewAdminPage = mainAdminDir+"/add_new_admin.jsp";
	
	//Others
	public static final String mainOthersDir = "/Others";
	
	//data source for connection pool
	public static final String resourcesLookup = "java:comp/env/jdbc/ics425";
	
	// message digest algorithm
	public static final String mdAlgorithm = "sha-512";
	/**
	 * DB back up properties
	 */
	
	public static final String DB_dumpExePath = "C:\\MyQL\\MySQL_Server_5.7\\bin";
	public static final String DB_backupPath = System.getProperty("user.home")+"\\Desktop\\School\\Classes\\ICS425\\ContactsGuroo_dbBackup";
	public static final String DB_HOST = "127.0.0.1";
	public static final String DB_PORT = "3306";
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "root";
	public static final String DB_NAME = "contact_guroo";
	
	/**
	 * Database query strings
	 */
	
	//persisting user registration data
	public static final String nameInsert = "INSERT INTO users(first_name, last_name, date_joined) VALUES(?,?, curdate())";
	public static final String addressInsert = "INSERT INTO u_address(u_id, street, city, state, zip) VALUES (?,?,?,?,?)";
	public static final String contactInsert = "INSERT INTO u_contact_info(u_id, phone, email) VALUES (?,?,?)";
	public static final String loginInfoInsert = "INSERT INTO u_login_info(u_id, username, password) VALUES (?,?,?)";
	
	// persisting contact data
	public static final String c_nameInsert = "INSERT INTO contacts(u_id, first_name, last_name) VALUES(?,?,?)";
	public static final String c_addressInsert = "INSERT INTO c_address(c_id, street, city, state, zip) VALUES (?,?,?,?,?)";
	public static final String c_phoneInsert = "INSERT INTO c_phones(c_id, phone) VALUES (?,?)";
	public static final String c_commentInsert = "INSERT INTO c_comments(c_id, comment) VALUES (?,?)";
	
	// updating contact data
	public static final String c_nameUpdate = "UPDATE contacts SET first_name = ?, last_name = ?"
						+ " WHERE contacts.c_id = ?";
	public static final String c_addressUpdate = "UPDATE c_address"
						+ " SET street = ?, city = ?, state = ?, zip = ?"
						+ " WHERE c_address.c_id = ?";
	public static final String c_phoneUpdate = "UPDATE c_phones SET phone = ? WHERE p_id = ?";
	public static final String c_commentUpdate = "UPDATE c_comments SET comment = ? WHERE cm_id = ?";
	
	// updating user data
	public static final String userUpdate = "UPDATE users SET first_name = ?, last_name = ? WHERE users.u_id = ?";
	public static final String u_addressUpdate = "UPDATE u_address SET street = ?, city = ?,"
						+" state = ?, zip = ? WHERE u_address.u_id = ?";
	public static final String u_contactInfoUpdate = "UPDATE u_contact_info SET phone = ?, email = ?"
						+" WHERE u_contact_info.u_id = ?";
	public static final String u_userNameUpdate = "UPDATE u_login_info SET username = ? "
						+ "WHERE u_login_info.u_id = ?";
	public static final String u_passwordUpdate = "UPDATE u_login_info SET password = ? "
						+ "WHERE u_login_info.u_id = ?";
 	
 
	//User Collection
	public static final String allUsersQuery = "SELECT * FROM users INNER JOIN u_login_info WHERE users.u_id = u_login_info.u_id";
	
	//Address book
	public static final String allContactsQuery = "SELECT * FROM contacts INNER JOIN c_address"+ 
						" WHERE contacts.u_id = ?"+
						" AND contacts.c_id = c_address.c_id";
	public static final String allPhonesQuery = "SELECT * FROM c_phones WHERE c_phones.c_id = ?";
	public static final String allCommentsQuery = "SELECT * FROM c_comments WHERE c_comments.c_id = ?";
	
	// Admin
	public static final String getAdminQuery = "SELECT * FROM admins WHERE username = ? AND password = ?";
	public static final String adminInsert = "INSERT INTO admins (first_name, last_name, username, password, master)"
						+" VALUES (?, ?, ?, ?,?)";
	public static final String adminAccountDelete = "DELETE FROM admins WHERE admins.a_id = ?";
	
	// Admin Collection
	public static final String allAdminsQuery = "SELECT * FROM admins";
	
	// User
	public static final String getUserQuery = "SELECT * FROM users INNER JOIN u_login_info INNER JOIN u_contact_info"
						+ " WHERE  users.u_id = u_login_info.u_id"
						+ " AND users.u_id = u_contact_info.u_id"
						+ " AND u_login_info.username = ?"
						+ " AND u_login_info.password = ?";
	public static final String getFullUserQuery = "SELECT * FROM users INNER JOIN u_login_info"
						+ " INNER JOIN u_address INNER JOIN u_contact_info"
						+ " WHERE  users.u_id = u_login_info.u_id"
						+ " AND users.u_id = u_address.u_id"
						+ " AND users.u_id = u_contact_info.u_id"
						+ " AND users.u_id = ?";
	public static final String userAccountDelete = "DELETE FROM users WHERE users.u_id = ?";
	
	
	// Contact
	public static final String removeContact = "DELETE FROM contacts WHERE contacts.c_id = ?";
	public static final String userContactsDelete = "DELETE FROM contacts WHERE contacts.u_id = ?";

	
	// Phone
	public static final String removePhone = "DELETE FROM c_phones WHERE p_id = ?";
	
	
	// Comment
	public static final String removeComment = "DELETE FROM c_comments WHERE cm_id = ?";
	
	//Email props
	public static final String smtpHost = "smtp.gmail.com";
	public static final String emailAcccount = "contactsguroo@gmail.com";
	public static final String emailPassword = "Enou1984";
	
	// verify account
	public static final String getUIDQuery = "SELECT u_id FROM u_contact_info WHERE email = ?";
	
	// get all emails
	private static final String getAllEmails = "SELECT email FROM u_contact_info"; 
	
	// get username by email
	public static final String getUsernameByEmail = "SELECT username FROM u_contact_info INNER JOIN u_login_info"
						+" WHERE u_contact_info.u_id = u_login_info.u_id"
						+" AND u_contact_info.email = ?";
	
	/** 
	 * email static methods 
	 */
	
	public static String createHTMLEmail(String subject, String message, String name, String email, String password){
		String mailHTML = "";
		mailHTML += "<html><body>";
		mailHTML += "<p>From: "+name + " ( " + email + " )</p>";
		mailHTML += "<p>Subject: "+subject +"</p>";
		mailHTML += "<p>Message: "+message+" ";
		mailHTML += "<font color='blue'>"+password+"</font></p>";
		mailHTML += "</body></html>";
		return mailHTML;
	}
	
	public static String createHTMLEmail(String subject, String message, String name, String email){
		return createHTMLEmail(subject, message, name, email, "");
	}
	
	public static Connection getConnection(){
		ConnectionPool pool = ConnectionPool.getInstance();
		return pool.getConnection();
	}
	
	private static Connection conn = getConnection();
	
	public static ArrayList<String> retrieveEmailList(){
		ArrayList<String> emailList = new ArrayList<String>();
		Statement statement = null;
		ResultSet result = null;
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(getAllEmails);
			while (result.next()){
				emailList.add(result.getString("email"));
			}
			return emailList;
		} catch (SQLException e) {
			
		}finally{
				try {
					if(conn != null)conn.close();
					if(result != null)result.close();
					if(statement != null)statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}

}
