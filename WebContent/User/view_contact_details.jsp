
<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>

<%
/* User user = (User) session.getAttribute("user"); */
if(user == null){
	response.sendRedirect(request.getContextPath()+Config.HOME);
}
%>
<div id="content" class="responsive-text">
	<h2>View Detials </h2><br><br>

	<table class="data-table">
	<%
	Contact contact = (Contact) request.getAttribute("contact");
	if(contact != null){
		
	%>
		<tr>
			<td>First Name</td>	
			<td><%=contact.getFirstName() %></td>
		</tr>
		<tr>
			<td>Last Name</td>	
			<td><%=contact.getLastName() %></td>
		</tr>
		<tr>
			<td>Address</td>	
			<td><%=contact.getAddress().toString() %></td>
		</tr>
		<tr>
			<td>Phones</td>	
			<td><%=contact.getPhones().toString() %></td>
		</tr>
		<tr>
			<td>Comments</td>	
			<td><%=contact.getComments().toString()%></td>
		</tr>

	<%} %>


	</table>
	<br>
	<form action="${pageContext.request.contextPath}/User/manage_contacts.jsp" method="post">
		<input type="submit" name="submit" value=<%=Config.RETURN %>>
	</form>
</div>

<div id="aside" class="responsive-text">
	<%@ include file="../templates/user-aside.jsp" %>
</div>
<%@ include file="../templates/footer.jsp" %>