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
import com.ics425.models.admin.*;

/**
 * Servlet implementation class ViewAllAdminsServlet
 */
@WebServlet("/ViewAllAdmins")
public class ViewAllAdminsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAllAdminsServlet() {
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
		if( admin == null){
			sc.getRequestDispatcher(Config.adminLoginPage).forward(request, response);
		}
		else{
			if(session.getAttribute("adminCollection") == null){
				AdminCollection adminCollection = AdminCollection.queryAllAdmins();
				session.setAttribute("adminCollection", adminCollection);							
			}
			// forward to view all users;
			RequestDispatcher rd = sc.getRequestDispatcher(Config.viewAllAdminsPage);
			rd.forward(request, response);
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
