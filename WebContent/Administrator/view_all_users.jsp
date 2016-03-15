<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>
<%
Admin admin = (Admin)session.getAttribute("admin");
if(admin == null){
	response.sendRedirect(request.getContextPath()+Config.adminLoginPage);
}
String errorMsg = (String) request.getAttribute("errorMsg");
String successMsg = (String) request.getAttribute("successMsg");
%>
 
<div id="content" class="responsive-text">
	<div class="errorMessage">
		<%=errorMsg != null? errorMsg: "" %>
	</div>
	<div class="greenMessage">
		<%=successMsg != null? successMsg: "" %>
	</div>
	<h2>All Users </h2><br><br>
	<table class="data-table">
		<thead>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Username</th>
			
			<%
				if(admin.isMaster()){
					%>
					<th>Delete</th>
					<% 
				}
			%>
			
		</thead>
		<tbody>
	<%
	UserCollection uc = (UserCollection) session.getAttribute("userCollection");
	if(uc != null){
		for(User u: uc.getUserList()){
			UserLoginInfo uli = u.getUserLoginInfo();
	%>

				<tr>
					<td><%=u.getFirstName() %></td>
					<td><%=u.getLastName() %></td>
					<td><%=uli.getUsername() %></td>
			<%
				if(admin.isMaster()){				
					%>
					<td>
						<form action="${pageContext.request.contextPath}/DeleteUser" method="post">
							<input type="hidden" name="u_id" value=<%=u.getU_id() %>>
							<input type="submit" name="submit" value=<%=Config.DELETE %>>
						</form>
					</td>
					<% 
				}
			%>
				</tr>

			
	<% }
	}
	%>
		</tbody>
	</table>

</div>
<div id="aside" class="responsive-text">
	<%@ include file="../templates/admin-aside.jsp" %>
</div>
<%@ include file="../templates/footer.jsp" %>