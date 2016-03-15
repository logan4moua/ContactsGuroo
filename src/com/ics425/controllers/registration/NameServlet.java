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
import com.ics425.models.*;
import com.ics425.models.user.User;

/**
 * Servlet implementation class NameServlet
 */
@WebServlet("/Name")
public class NameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NameServlet() {
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
			String next = Config.getAddressContactPage;
			String previous = Config.HOME;
			String current = Config.getNamePage;
			String url = next;
			
			if(action.compareTo(Config.PREVIOUS) == 0){
				url = previous;
			}
			

			String firstName = request.getParameter("firstName").replaceAll(Config.SCRIPT_REGEX, "").trim();		
			String lastName = request.getParameter("lastName").replaceAll(Config.SCRIPT_REGEX, "").trim();
			
			if(firstName != "")
				user.setFirstName(firstName);
			if(lastName != ""){
				user.setLastName(lastName);			
			}

							
			if(url != previous & (firstName == "" | lastName == "")){
				request.setAttribute(Config.errorMsg, Config.emptyNameFieldError);
				url = current;
			}
								
			RequestDispatcher rd = sc.getRequestDispatcher(url);
			rd.forward(request, response);
			
		}
		
	}

}
