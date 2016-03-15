package com.ics425.controllers.userContactManager;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ics425.config.Config;
import com.ics425.models.contact.AddressBook;
import com.ics425.models.contact.Contact;
import com.ics425.models.user.User;

/**
 * Servlet implementation class SearchContactServlet
 */
@WebServlet("/SearchContact")
public class SearchContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchContactServlet() {
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
			String url = Config.searchResultPage;
			if(request.getParameter("search") == null){
				url = Config.searchContactPage;
			}else{
				
				String firstName_s = request.getParameter("firstName_search");
				String lastName_s = request.getParameter("lastName_search");
				
				AddressBook ab = (AddressBook)session.getAttribute("addressBook");
				AddressBook found = ab.matches(firstName_s, lastName_s);
				request.setAttribute("found", found);
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
