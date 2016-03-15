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
import com.ics425.models.user.*;

/**
 * Servlet implementation class AddNewUserServlet
 */
@WebServlet("/AddNewUser")
public class AddNewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewUserServlet() {
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
			String url = Config.viewAllUsersPage;
			String action = (String)request.getParameter("action");
			if(action.compareTo(Config.COMMIT)==0){			
				
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String street = request.getParameter("street");
				String city = request.getParameter("city");
				String state = request.getParameter("state");
				String zip = request.getParameter("zip");
				String phone = request.getParameter("phone");
				String email = request.getParameter("email");				
				String username = request.getParameter("username");
				String password1 = request.getParameter("password1");
				String password = request.getParameter("password");
				
				Address address = new Address(street, city, state, zip);
				ContactInfo ci = new ContactInfo(phone, email);
				UserLoginInfo uli = new UserLoginInfo(username, password);
				
				User newUser = new User(firstName, lastName, address, ci, uli);
				session.setAttribute("newUser", newUser);
				
				
				if(firstName=="" | lastName=="" | username=="" | password1=="" | street=="" | city==""
						| state=="" | zip=="" | phone=="" | email==""){
					request.setAttribute(Config.errorMsg, Config.emptyFieldErrorMsg);
					url = Config.addNewUserPage;
				}
				else if(password.compareTo(password1) != 0){
					request.setAttribute(Config.errorMsg, Config.passwordNotMatchedMessage);
					url = Config.addNewUserPage;
				}else{
					if(! PasswordUtil.isValidPassword(password)){
						request.setAttribute(Config.errorMsg, Config.passwordMissedCriteriaMessage);
						url = Config.addNewUserPage;
					}else{
						
						newUser.persist();
						UserCollection uc = (UserCollection)session.getAttribute("userCollection");
						uc.add(newUser);
						request.setAttribute(Config.successMsg, Config.addedNewUserSuccessMsg);
						if(session.getAttribute("newUser") != null)
							session.removeAttribute("newUser");
					}
					
				}
				
			}else{ // cancel
				if(session.getAttribute("newUser") != null)
					session.removeAttribute("newUser");
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
