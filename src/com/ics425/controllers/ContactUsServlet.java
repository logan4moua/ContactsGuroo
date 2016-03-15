package com.ics425.controllers;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ics425.config.Config;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Servlet implementation class ContactUsServlet
 */
@WebServlet("/ContactUs")
public class ContactUsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactUsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = Config.emailAcccount;
		String password = Config.emailPassword;
		String to = Config.emailAcccount;
		String from = Config.emailAcccount;
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		
		//String host = "smtp.gmail.com";
				
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
			msg.setContent(Config.createHTMLEmail(subject, message, name, email), "text/html");
			
			Transport.send(msg);
			
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		request.setAttribute(Config.successMsg, Config.mailSentSuccessMsg);
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
