<%@page import="com.ics425.config.*" %>	
<h3>Admin Login</h3><br>
	<form action="${pageContext.request.contextPath}/AdminLogin" method="post">
	<table>

		<tr>
			<td>username:</td>
			<td><input type="text" name="admin_username" value="" ></td>
		</tr>
		<tr>
			<td>password:</td>
			<td><input type="password" name="admin_password" value = "" ></td>
		</tr>
		<tr></tr>
		<tr>
			<td></td>
			<td><input type="submit" name="signin" value="<%=Config.SIGN_IN %>" /></td>
		</tr>
	</table>

	</form>