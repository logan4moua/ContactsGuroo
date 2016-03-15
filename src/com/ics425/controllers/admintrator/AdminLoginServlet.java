package com.ics425.controllers.admintrator;

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
import com.ics425.models.admin.Admin;
import com.ics425.models.user.UserCollection;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/AdminLogin")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletContext sc = getServletContext();
		Admin admin = (Admin)session.getAttribute("admin");
		
		String admin_username = request.getParameter("admin_username");
		String admin_password = request.getParameter("admin_password");
		if( admin == null){
			if(admin_username == "" | admin_password == "")
				sc.getRequestDispatcher(Config.adminLoginPage).forward(request, response);
			else
				admin = new Admin(admin_username, admin_password);	
		}
		
		String url = Config.viewAllUsersPage;
		if(! admin.isAuthenticated()){
			request.setAttribute("signin_error", Config.signinErrorMsg);
			url = Config.adminLoginPage;
		}
		else{
			UserCollection userCollection = UserCollection.queryAllUsers();
			session.setAttribute("userCollection", userCollection);
			session.setAttribute("admin", admin);
			session.setAttribute("contextPath", sc.getContextPath());
			
			// forward to view all users;
		}
		sc.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
