package com.ics425.controllers.registration;

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
import com.ics425.models.*;
import com.ics425.models.user.User;
import com.ics425.models.user.UserLoginInfo;

/**
 * Servlet implementation class UserLoginInfoServlet
 */
@WebServlet("/UserLoginInfo")
public class UserLoginInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginInfoServlet() {
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
		User user = (User)session.getAttribute("newUser");
		if( user == null){
			sc.getRequestDispatcher(Config.HOME).forward(request, response);// if anyone try to access this page by fault, will be redirect to index.jsp
		}
		else{
			
			String action = (String)request.getParameter("action");
			String next = Config.confirmationPage;
			String previous = Config.getAddressContactPage;
			String current = Config.getUserLoginInfoPage;
			String url = next;
			
			if(action.compareTo(Config.PREVIOUS)==0){
				url = previous;
			}else{
				String username = request.getParameter("username").trim().replaceAll(Config.SCRIPT_REGEX, "");
				String password = request.getParameter("password").trim().replaceAll(Config.SCRIPT_REGEX, "");
				String password1 = request.getParameter("password1").trim().replaceAll(Config.SCRIPT_REGEX, "");	
								
				if(!passwordMatched(password, password1)){
					session.setAttribute(Config.errorMsg, Config.passwordNotMatchedMessage);			
					url = current;			
				}
				else{
					if(!passwordMeetCriteria(password)){
						request.setAttribute(Config.errorMsg, Config.passwordMissedCriteriaMessage);			
						url = current;	
					}
					else{
						// create new user login information instance
						UserLoginInfo uli = new UserLoginInfo(username, password);
						user.setUserLoginInfo(uli);
						
						// Persists a user information to database
						user.persist();				
			
						session.setAttribute("user", user); // treated new user as logged in user
						session.removeAttribute("newUser");
						
					}				
				}
			}
			RequestDispatcher rd = sc.getRequestDispatcher(url);
			rd.forward(request, response);
		}	
	}
	/**
	 * Check if the two passwords are the same
	 * @param p1
	 * @param p2
	 * @return
	 */
	private boolean passwordMatched(String p1, String p2){
		return p1.compareTo(p2) == 0;
	}
	
	/**
	 * Check if the password meet all criteria
	 * @param password
	 * @return
	 */
	private boolean passwordMeetCriteria(String password){
		return PasswordUtil.isValidPassword(password);
	}	

}
