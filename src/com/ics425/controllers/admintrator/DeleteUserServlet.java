package com.ics425.controllers.admintrator;

import java.io.IOException;

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
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
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

		if(admin == null){
			sc.getRequestDispatcher(Config.adminLoginPage).forward(request, response);
		}
		else{
			int u_id = Integer.parseInt(request.getParameter("u_id"));
			UserCollection uc = (UserCollection)session.getAttribute("userCollection");
			if(!uc.remove(u_id)){
				request.setAttribute(Config.errorMsg, Config.removingUserFailedMsg);
			}
			else{
				request.setAttribute(Config.successMsg, Config.removedUserSuccessMsg);	
			}
			
			sc.getRequestDispatcher(Config.viewAllUsersPage).forward(request, response);
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
