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
	<h2>All Admins</h2><br ><br>
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
	AdminCollection ac = (AdminCollection) session.getAttribute("adminCollection");
	if(ac != null){
		for(Admin a: ac.getAdminList()){
	%>
			<tr>
				<td><%=a.getFirstName() %></td>
				<td><%=a.getLastName() %></td>
				<td><%=a.getUsername() %></td>
				
			<%
				if(admin.isMaster()){				
					%>
					<td>
						<form action="${pageContext.request.contextPath}/DeleteAdmin" method="post">
							<input type="hidden" name="a_id" value=<%=a.getA_id() %>>
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