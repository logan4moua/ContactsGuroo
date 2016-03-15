<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>

<%
String email="";
/* User user = (User)session.getAttribute("user"); */
if(user != null){
	email = user.getContactInfo().getEmail();
}
%>
<div id="content" class="responsive-text">

<h2>Contact Us</h2><br><br>

<form action="${pageContext.request.contextPath}/ContactUs" method="post">
	<table>
		<tr>
			<td>Name:</td>
			<td><input type="text" name="name" value="<%=welcomeUsername%>"></td>
		</tr>
		<tr>
			<td>Email:</td>
			<td><input type="email" name="email" value="<%=email%>"></td>
		</tr>
		<tr>
			<td>Subject:</td>
			<td><input type="text" name="subject" value=""></td>
		</tr>
		<tr>
			<td>Message:</td>
			<td><textarea name="message" rows=10 cols=40></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="submit" value=<%=Config.SEND %>></td>
		</tr>
	</table>	
</form>
</div>

<div id="aside" class="responsive-text">

	<%if(session.getAttribute("user") != null){%>
		<%@ include file="../templates/user-aside.jsp" %>
	<%}else if(session.getAttribute("admin") != null){ %>
		<%@ include file="../templates/admin-aside.jsp" %>
	<%}else{ %>
		<%@ include file="../User/user_login_form.jsp" %>
	<%} %>
	
</div>

<%@ include file="../templates/footer.jsp" %>