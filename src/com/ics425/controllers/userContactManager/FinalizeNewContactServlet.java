package com.ics425.controllers.userContactManager;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ics425.config.Config;
import com.ics425.models.contact.*;
import com.ics425.models.user.User;

/**
 * Servlet implementation class FinalizeNewContactServlet
 */
@WebServlet("/FinalizeNewContact")
public class FinalizeNewContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalizeNewContactServlet() {
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
			String action = request.getParameter("action");
			String url = Config.manageContactsPage;
			if(action.compareTo(Config.CANCEL) == 0){
				session.removeAttribute("newContact");
				
			}
			else{
				
				Contact contact = (Contact)session.getAttribute("newContact");
				CommentCollection cc = new CommentCollection();
				cc.add(new Comment(request.getParameter("comment_1")));
				cc.add(new Comment(request.getParameter("comment_2")));
				cc.add(new Comment(request.getParameter("comment_3")));
				
				contact.setComments(cc);
							
				if(action.compareTo(Config.PREVIOUS) == 0){
					url = Config.addNewContactPage;
				}
				else{
										
					contact.persist();
					
					session.setAttribute("addressBook", AddressBook.queryMyContacts(user.getU_id()));
					session.removeAttribute("newContact");
					
					request.setAttribute(Config.successMsg, Config.contactAddedSuccessMsg);
				}
				
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
