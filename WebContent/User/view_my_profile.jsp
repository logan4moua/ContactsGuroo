<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>

<%
/* user = (User) session.getAttribute("userProfile"); */
if(user == null){
	response.sendRedirect(request.getContextPath()+Config.HOME);
}
String successMsg = (String)request.getAttribute("successMsg");
%>
<div id="content" class="responsive-text">
	<div class="greenMessage">
		<%=successMsg != null? successMsg: "" %>
	</div>
	<h2>View My Profile </h2><br><br>

	<table class="data-table">
	<%

	if(user != null){
		
	%>
		<tr>
			<td>First Name</td>	
			<td><%=user.getFirstName() %></td>
		</tr>
		<tr>
			<td>Last Name</td>	
			<td><%=user.getLastName() %></td>
		</tr>
		<tr>
			<td>Address</td>	
			<td><%=user.getAddress().toString() %></td>
		</tr>
		<tr>
			<td>Contact Infomation</td>	
			<td><%=user.getContactInfo().toString() %></td>
		</tr>
		<tr>
			<td>Login Information</td>	
			<td><%=user.getUserLoginInfo().getUsername()%></td>
		</tr>
		<tr>
			<td></td>
			<td>
			
				<table id="table-submit-button">
					<tr>
						<td class="border-none">
							<form action="${pageContext.request.contextPath}/User/edit_profile.jsp" method="post">				
								<input type="submit" name="submit" value="Edit" />
							</form>
							
						</td>
						<td></td>
						<td>
							<form action="${pageContext.request.contextPath}/User/change_password.jsp" method="post">				
								<input type="submit" name="submit" value="Change password" />
							</form>
						</td>
					</tr>
				</table>
			</td>
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