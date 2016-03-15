
<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>

<div id="content" class="responsive-text">

	<%@ include file="../Registration/user_signup.jsp" %>

</div>
<div id="aside" class="responsive-text">
	<h3>Forgot Password</h3><br>

	<form action="${pageContext.request.contextPath}/SendTempPassword" method="post">
	Send me a tempolary password
	<table>
		<tr>
			<td>Enter email:</td>
			<td><input type="email" name="email"></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="submit" value=<%=Config.SEND %>></td>
		</tr>
	</table>
	</form>
</div>


<%@ include file="../templates/footer.jsp" %>