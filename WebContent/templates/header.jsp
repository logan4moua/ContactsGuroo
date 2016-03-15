<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.ics425.models.user.*, com.ics425.models.contact.*, com.ics425.config.*, com.ics425.models.admin.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=Config.TITLE %></title>


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS_Resources/styles-1.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS_Resources/styles.css" />


</head>
<body>
<%
	String welcomeUsername = "";
	User user = (User) session.getAttribute("user");
	if(user != null){
		welcomeUsername = user.getUserLoginInfo().getUsername();
	} 
%>
	<div id="header">
		<h1><%=Config.TITLE %></h1><br>
		<%=welcomeUsername == ""? "Welcome": welcomeUsername %>
	</div>