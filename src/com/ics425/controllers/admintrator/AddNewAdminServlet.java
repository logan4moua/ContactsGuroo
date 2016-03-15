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
import com.ics425.miscTools.PasswordUtil;
import com.ics425.models.admin.Admin;
import com.ics425.models.admin.AdminCollection;

/**
 * Servlet implementation class AddNewAdminServlet
 */
@WebServlet("/AddNewAdmin")
public class AddNewAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewAdminServlet() {
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
			String url = Config.viewAllAdminsPage;
			if(!admin.isMaster()){
				sc.getRequestDispatcher(url).forward(request, response);
			}
			
			String action = request.getParameter("action");
			if(action.compareTo(Config.CANCEL) == 0){
				if(session.getAttribute("newAdmin") != null)
					session.removeAttribute("newAdmin");
				AdminCollection ac = (AdminCollection) session.getAttribute("adminColleciton");
				if(ac == null){
					session.setAttribute("adminCollection", AdminCollection.queryAllAdmins());
				}
				sc.getRequestDispatcher(url).forward(request, response);
			}
			
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			boolean master = Boolean.parseBoolean(request.getParameter("master"));
			String username = request.getParameter("username");
			String password2 = request.getParameter("password2");
			String password1 = request.getParameter("password1");
			
			Admin newAdmin = new Admin(firstName, lastName, username, password1, master);
			session.setAttribute("newAdmin", newAdmin);
			
			if(firstName=="" | lastName=="" | username=="" | password1==""){
				url = Config.addNewAdminPage;
				request.setAttribute(Config.errorMsg, Config.emptyFieldErrorMsg);
			}
			else if(password1.compareTo(password2)!= 0){
				url = Config.addNewAdminPage;
				request.setAttribute(Config.errorMsg, Config.passwordNotMatchedMessage);
			}else{
				
				if(! PasswordUtil.isValidPassword(password1)){
					request.setAttribute(Config.errorMsg, Config.passwordMissedCriteriaMessage);
					url = Config.addNewAdminPage;
				}else{
					
					newAdmin.persist();	
					AdminCollection ac = (AdminCollection)session.getAttribute("adminCollection");
					if(ac == null){
						ac = AdminCollection.queryAllAdmins();
					}else
						ac.add(newAdmin);
				}
				
			}

			RequestDispatcher rd = sc.getRequestDispatcher(url);
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
