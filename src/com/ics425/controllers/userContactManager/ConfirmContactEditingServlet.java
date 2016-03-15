package com.ics425.controllers.userContactManager;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ics425.config.Config;
import com.ics425.models.contact.AddressBook;
import com.ics425.models.contact.Comment;
import com.ics425.models.contact.CommentCollection;
import com.ics425.models.contact.Contact;
import com.ics425.models.contact.Phone;
import com.ics425.models.contact.PhoneCollection;
import com.ics425.models.user.Address;
import com.ics425.models.user.User;

/**
 * Servlet implementation class ConfirmContactEditingServlet
 */
@WebServlet("/ConfirmContactEditing")
public class ConfirmContactEditingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmContactEditingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletContext sc = getServletContext();
		User user = (User)session.getAttribute("user");
		if(user == null){
			sc.getRequestDispatcher(Config.HOME).forward(request, response);
		}
		else{
			String action = (String) request.getParameter("action");
			String url = Config.manageContactsPage;
			if(action.compareTo(Config.CANCEL)==0){
				// do nothing
			}
			else{
				
				Contact contact = (Contact)session.getAttribute("editingContact");
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String street = request.getParameter("street");
				String city = request.getParameter("city");			
				String state = request.getParameter("state");
				String zip = request.getParameter("zip");
				String phone_1 = request.getParameter("phone_1");
				String phone_2 = request.getParameter("phone_2");
				String comment_1 = request.getParameter("comment_1");
				String comment_2 = request.getParameter("comment_2");
				String comment_3 = request.getParameter("comment_3");
				
				contact.setFirstName(firstName);
				contact.setLastName(lastName);
				
				Address address = contact.getAddress();
				address.setStreet(street);
				address.setCity(city);
				address.setState(state);
				address.setZip(zip);
				
				PhoneCollection pc = contact.getPhones();
				Phone[] phoneList = pc.getPhoneList();
				phoneList[0].setPhone(phone_1);
				phoneList[1].setPhone(phone_2);
				pc.setPhoneList(phoneList);
				
				CommentCollection cc = contact.getComments();
				ArrayList<Comment> cl = cc.getCommentList();
				cl.get(0).setComment(comment_1);
				cl.get(1).setComment(comment_2);
				cl.get(2).setComment(comment_3);
				cc.setCommentList(cl);
				
				contact.update();
				
				session.setAttribute("addressBook", AddressBook.queryMyContacts(user.getU_id()));
				session.removeAttribute("editingContact");
				request.setAttribute(Config.successMsg, Config.contactUpdatedSuccessMsg);
			}
			sc.getRequestDispatcher(url).forward(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
