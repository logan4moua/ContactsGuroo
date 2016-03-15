package com.ics425.controllers.userContactManager;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ics425.config.Config;
import com.ics425.models.contact.AddressBook;
import com.ics425.models.user.*;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/UserLogin")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
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
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (user == null){
			user = new User();
			UserLoginInfo uli = new UserLoginInfo(username, password);
			user.setUserLoginInfo(uli);
		}
		
		String url = Config.manageContactsPage;
		
		if( ! user.isAuthenticated() ){
			request.setAttribute(Config.errorMsg, Config.signinErrorMsg);
			url = Config.HOME;
			
		}
		else{
			
			//
			AddressBook addressBook = (AddressBook)session.getAttribute("addressBook");
			if(addressBook == null){
				addressBook = AddressBook.queryMyContacts(user.getU_id());
				session.setAttribute("addressBook", addressBook);
				
			}
			session.setAttribute("user", user);	
			session.setAttribute("contextPath", sc.getContextPath());
	
			// forward to view all users;
		}
		RequestDispatcher rd = sc.getRequestDispatcher(url);
		rd.forward(request, response);
			
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
