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
import com.ics425.miscTools.PasswordUtil;
import com.ics425.miscTools.StringToMessageDigestConverter;
import com.ics425.models.user.User;
import com.ics425.models.user.UserLoginInfo;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/ChangePassword")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
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
			String url = Config.viewMyProfilePage;
			if(action.compareTo(Config.CONFIRM)== 0){
				
				String old_pass = request.getParameter("old_pass");
				String new_pass1 = request.getParameter("new_pass1").trim().replace(Config.SCRIPT_REGEX, "");
				String new_pass2 = request.getParameter("new_pass2").trim().replace(Config.SCRIPT_REGEX, "");
				
				if(old_pass=="" | new_pass1==""){
					request.setAttribute(Config.errorMsg, Config.emptyFieldErrorMsg);
					url = Config.changePasswordPage;
				}
				else if(new_pass1.compareTo(new_pass2)!= 0){
					url = Config.changePasswordPage;
					request.setAttribute(Config.errorMsg, Config.passwordNotMatchedMessage);
				}else{
					
					int u_id = user.getU_id();
					UserLoginInfo uli = user.getUserLoginInfo();
					String oldPassHex = StringToMessageDigestConverter.convert(old_pass);
					boolean changePassword = uli.changePassword(u_id, oldPassHex, new_pass1);
					if(changePassword){
						uli.setPassword(new_pass1);
						request.setAttribute(Config.successMsg, Config.passwordChangedSuccessMsg);
					}else{
						url = Config.changePasswordPage;
						request.setAttribute(Config.errorMsg, Config.passwordChangedErrorMsg);
					}
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
