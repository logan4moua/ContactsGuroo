<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>

<%
String errMsg = (String)request.getAttribute("errorMsg");
%>

<div id="content" class="responsive-text">
	<div class="errorMessage">
		<%=errMsg != null? errMsg: "" %>
	</div>

	<h2>Change Password</h2><br><br>
	<form action="${pageContext.request.contextPath}/ChangePassword" method="post">
	<table>
		<tr>
			<td>Old password:</td>
			<td><input type="password" name="old_pass"></td>
		</tr>
		<tr>
			<td>New password:</td>
			<td><input type="password" name="new_pass1"></td>
		</tr>
		<tr>
			<td>Confirm new password:</td>
			<td><input type="password" name="new_pass2"></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
		</tr>
	</table>

		<input type="submit" name="action" value=<%=Config.CANCEL %>>
		<input type="submit" name="action" value=<%=Config.CONFIRM %>>
	</form>

</div>
<div id="aside" class="responsive-text">
	<%@ include file="../templates/user-aside.jsp" %>
</div>


<%@ include file="../templates/footer.jsp" %>