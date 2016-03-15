<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>

<%
Admin admin = (Admin)session.getAttribute("admin");
if(admin == null){
	response.sendRedirect(request.getContextPath()+Config.adminLoginPage);
}
if(!admin.isMaster()){
	response.sendRedirect(request.getContextPath()+Config.viewAllAdminsPage);
}
String erMsg = (String)request.getAttribute("errorMsg");
%>
<div id="content" class="responsive-text">
	<div class="errorMessage">
		<%=(erMsg != null? erMsg: "") %>
	</div>
	<h2>New Admin</h2><br><br>
	<form action="${pageContext.request.contextPath}/AddNewAdmin" method="post">
		<table>
			<tr>
				<td>First Name: </td>
				<td><input type="text" name="firstName" value="" /></td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><input type="text" name="lastName" value="" /></td>
			</tr>
			<tr>
				<td>Admin type: </td>
				<td>
					<select name="master">
						<option value="false" selected>Standard Admin</option>
						<option value="true">Master Admin</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Username: </td>
				<td><input type="text" name="username" value="" /></td>
			</tr>
			
			<tr>
				<td>Password: </td>
				<td><input type="password" name="password1" value="" /></td>
			</tr>
			<tr>
				<td>Confirm Password: </td>
				<td><input type="password" name="password2" value="" /></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td><table>
						<tr>
							<td><input type="submit" name="action" value=<%=Config.CANCEL %> /></td>
							<td><input type="submit" name="action" value=<%=Config.COMMIT %> /></td>
						</tr>				
					</table>
				</td>
			</tr>
	
		</table>
	</form>

</div>

<div id="aside" class="responsive-text">

	<%@ include file="../templates/admin-aside.jsp" %>
</div>
<%@ include file="../templates/footer.jsp" %>