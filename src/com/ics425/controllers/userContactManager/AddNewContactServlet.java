package com.ics425.controllers.userContactManager;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ics425.config.Config;
import com.ics425.models.contact.*;
import com.ics425.models.user.*;

/**
 * Servlet implementation class AddNewContactServlet
 */
@WebServlet("/AddNewContact")
public class AddNewContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewContactServlet() {
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
			Contact contact = (Contact)session.getAttribute("newContact");
			String action = request.getParameter("action");
			String url = Config.addCommentsPage;
			if(action.compareTo(Config.CANCEL) == 0){
				if(contact != null)
					session.removeAttribute("newContact");
				url = Config.manageContactsPage;
			}
			else{	
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String street = request.getParameter("street");
				String city = request.getParameter("city");
				String state = request.getParameter("state");
				String zip = request.getParameter("zip");
				String phone_1 = request.getParameter("phone_1");
				String phone_2 = request.getParameter("phone_2");
				
				Address address = new Address(street, city, state, zip);
				
				PhoneCollection phones = new PhoneCollection();
				phones.add(new Phone(phone_1));
				phones.add(new Phone(phone_2));
				
				contact = new Contact(user.getU_id(), firstName, lastName, address, phones);
				session.setAttribute("newContact", contact );
				
				if(firstName=="" | lastName==""){
					request.setAttribute(Config.errorMsg, Config.emptyNameFieldError);
					url = Config.addNewContactPage;
				}

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
