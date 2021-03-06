package com.ics425.controllers;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ics425.config.Config;
import com.ics425.miscTools.PasswordUtil;

/**
 * Servlet implementation class SendTempPasswordServlet
 */
@WebServlet("/SendTempPassword")
public class SendTempPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendTempPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String toEmail = request.getParameter("email");
		String tempPassword = PasswordUtil.assignTempPassword(toEmail);
		
		if(tempPassword != ""){
			
			String username = Config.emailAcccount;
			String password = Config.emailPassword;
			String to = toEmail;
			String from = Config.emailAcccount;
			String name = "Contacts Guroo Webmaster";
			String subject = "Your tempolary password";
			String message = "Thanks for contacting us. Here is your tempolary password: ";
			
			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", Config.smtpHost);
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.port", "587");
			
			Authenticator auth = new Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(username, password);
				}
			};
			
			Session mailSession = Session.getDefaultInstance(properties, auth);
			
			Message msg = new MimeMessage(mailSession);
			
			try {
				msg.setSubject(subject);
				msg.setFrom(new InternetAddress(from, name));
				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				msg.setContent(Config.createHTMLEmail(subject, message, name, from, tempPassword), "text/html");
				
				Transport.send(msg);
				request.setAttribute(Config.successMsg, Config.passwordSentToEmailMsg);
				
			} catch (MessagingException e) {
				
				e.printStackTrace();
			}
		}
		
		getServletContext().getRequestDispatcher(Config.HOME).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
