package com.ics425.models.contact;

import java.sql.*;
import java.util.ArrayList;

import com.ics425.config.Config;
import com.ics425.dbconnection.ConnectionPool;
import com.ics425.models.user.*;

public class AddressBook {
	private static Connection conn = null;
	private ArrayList<Contact> contactList;
	
	public AddressBook(){
		contactList = new ArrayList<Contact>();
	}
	
	public synchronized boolean add(Contact arg0) {
		return contactList.add(arg0);
	}
	
	public synchronized boolean remove(int c_id){
		for(Contact c: contactList){
			if (c_id == c.getC_id()){
				c.remove();

				return contactList.remove(c);
			}
		}
		return false;
	}
	public String toString() {
		return contactList.toString();
	}
	
	
	public synchronized ArrayList<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(ArrayList<Contact> contactList) {
		this.contactList = contactList;
	}
	
	/**
	 * 
	 * @return an AddressBook belong to a person
	 * @param u_id current logged in user id
	 */
	public static AddressBook queryMyContacts(int u_id){
		AddressBook ab = new AddressBook();
		conn = Config.getConnection();
		
		PreparedStatement pStatement = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			pStatement = conn.prepareStatement(Config.allContactsQuery);
			pStatement.setInt(1, u_id);
			result = pStatement.executeQuery();
			

			while(result.next()){
				Contact contact = new Contact();
				contact.setFirstName(result.getString("first_name"));
				contact.setLastName(result.getString("last_name"));
				contact.setC_id(result.getInt("c_id"));
				
				Address address = new Address();
				address.setStreet(result.getString("street"));
				address.setCity(result.getString("city"));
				address.setState(result.getString("state"));
				address.setZip(String.valueOf(result.getInt("zip")));
				contact.setAddress(address);
				
				PhoneCollection phones = PhoneCollection.queryMyPhones(contact.getC_id(), conn);
				contact.setPhones(phones);
				
				CommentCollection comments = CommentCollection.queryMyComments(contact.getC_id(), conn);
				contact.setComments(comments);
				
				ab.add(contact);
				
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

		return ab;
	}
	/**
	 * 
	 * @param first name
	 * @param last name
	 * @return
	 */
	public AddressBook matches(String fname, String lname){
		AddressBook found = new AddressBook();
		for(Contact c: contactList){
			if(fname != "" && lname != ""){
				if(c.getFirstName().toLowerCase().contains(fname.toLowerCase()) 
						&& c.getLastName().toLowerCase().contains(lname.toLowerCase())){
					found.add(c);
				}			
			}
			else if(fname != ""){
				if(c.getFirstName().toLowerCase().contains(fname.toLowerCase()) ){
					found.add(c);
				}
			}
			else if (lname != ""){
				if(c.getLastName().toLowerCase().contains(lname.toLowerCase()) ){
					found.add(c);
				}
			}
			else{
				// do nothing
			}
		}
		return found;
	}
	
	/**
	 * get a contact by c_id
	 * @param c_id
	 * @return
	 */
	public Contact getContact(int c_id){
		for(Contact c: contactList){
			if(c_id == c.getC_id()){
				return c;
			}
		}
		return null;
	}
}
