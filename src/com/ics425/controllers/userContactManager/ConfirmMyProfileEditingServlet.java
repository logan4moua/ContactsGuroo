package com.ics425.controllers.userContactManager;

import java.awt.Desktop.Action;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ics425.config.Config;
import com.ics425.models.user.*;

/**
 * Servlet implementation class ConfirmMyProfileEditingServlet
 */
@WebServlet("/ConfirmMyProfileEditing")
public class ConfirmMyProfileEditingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmMyProfileEditingServlet() {
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
			if(action.compareTo(Config.CONFIRM)==0){
			
				user.setFirstName((String)request.getParameter("firstName"));
				user.setLastName((String)request.getParameter("lastName"));
				
				Address address = user.getAddress();
				address.setStreet(request.getParameter("street"));
				address.setCity(request.getParameter("city"));
				address.setState(request.getParameter("state"));
				address.setZip(request.getParameter("zip"));
				user.setAddress(address);
				
				ContactInfo ci = user.getContactInfo();
				ci.setEmail(request.getParameter("email"));
				ci.setPhone(request.getParameter("phone"));
				
				UserLoginInfo uli = user.getUserLoginInfo();
				uli.setUsername(request.getParameter("username"));				
			
				user.update();
				user.queryMyProfile();
				
				request.setAttribute(Config.successMsg, Config.profileUpdatedSuccessMsg);
				
			}
			
			sc.getRequestDispatcher(Config.viewMyProfilePage).forward(request, response);
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
