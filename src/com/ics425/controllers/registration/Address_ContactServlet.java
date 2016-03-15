package com.ics425.controllers.registration;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ics425.config.Config;
import com.ics425.models.*;
import com.ics425.models.user.Address;
import com.ics425.models.user.ContactInfo;
import com.ics425.models.user.User;

/**
 * Servlet implementation class AddressContactServlet
 */
@WebServlet("/Address_Contact")
public class Address_ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Address_ContactServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession();
		User newUser = (User) session.getAttribute("newUser");
		
		if( newUser == null){
			sc.getRequestDispatcher(Config.HOME).forward(request, response);// if anyone try to access this page by fault, will be redirect to index.jsp
		}
		else{
			
			String action = (String)request.getParameter("action");
			
			String url = Config.getUserLoginInfoPage;
			if(action.compareTo(Config.PREVIOUS)==0)
				url = Config.getNamePage;

			String street = request.getParameter("street").trim().replaceAll(Config.SCRIPT_REGEX, "");
			String city = request.getParameter("city").trim().replaceAll(Config.SCRIPT_REGEX, "");
			String state = request.getParameter("state").trim().replaceAll(Config.SCRIPT_REGEX, "");
			String zip = request.getParameter("zip").trim().replaceAll(Config.SCRIPT_REGEX, "");
			String phone = request.getParameter("phone").trim().replaceAll(Config.SCRIPT_REGEX, "");
			String email = request.getParameter("email").trim().replaceAll(Config.SCRIPT_REGEX, "");
			
			// created new address instance
			Address address = new Address(street, city, state, zip);
			
			// created new contact information instance
			ContactInfo ci = new ContactInfo(phone, email);			
			
			newUser.setAddress(address);
			newUser.setContactInfo(ci);
			
			if(url != Config.getNamePage &(street=="" | city=="" | state =="" | zip =="" | phone=="" | email=="")){
				request.setAttribute(Config.errorMsg, Config.emptyFieldErrorMsg);
				url = Config.getAddressContactPage;
			}else{
				
				ArrayList<String> inUseEmailList = (ArrayList<String>)session.getAttribute("emailList");
				if(inUseEmailList == null){
					inUseEmailList = Config.retrieveEmailList();
					session.setAttribute("emailList", inUseEmailList);
				}
				if(inUseEmailList.contains(email)){
					request.setAttribute(Config.errorMsg, Config.emailDuplicatedErrorMsg);
					url = Config.getAddressContactPage;
				}
			}
			RequestDispatcher rd = sc.getRequestDispatcher(url);
			rd.forward(request, response);
	
		}

	}

}
