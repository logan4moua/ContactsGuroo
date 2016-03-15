<%@page import="com.ics425.config.*" %>	
<h3>User Login</h3><br>
	<form action="${pageContext.request.contextPath}/UserLogin" method="post">
	<table>
		<tr>
			<td>username:</td>
			<td><input type="text" name="username" value="" ></td>
		</tr>
		<tr>
			<td>password:</td>
			<td><input type="password" name="password" value = "" ></td>
		</tr>
		<tr></tr>
		<tr>
			<td></td>
			<td><input type="submit" name="signin" value="<%=Config.SIGN_IN %>" /></td>
		</tr>
		<tr>
			<td></td>
			<td><a href="${pageContext.request.contextPath}/Others/forget_password.jsp">Forgot password</a></td>
		</tr>
		<tr>
			<td></td>
			<td><a href="${pageContext.request.contextPath}/Others/forget_username.jsp">Don't remember username</a></td>
		</tr>
		
	</table>
	</form>

