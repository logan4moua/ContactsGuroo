<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>
<%
/* User user = (User) session.getAttribute("user"); */
if(user == null){
	response.sendRedirect(request.getContextPath()+Config.HOME);
}
%>


<div id="content" class="responsive-text">
<h2>Search Contacts</h2><br><br>

<form action="${pageContext.request.contextPath}/SearchContact" method="post">
<table>
	<tr>
		<td>Enter first name or </td>
		<td><input type="text" name="firstName_search" placeholder="enter first name"></td>
	</tr>
	<tr>
		<td>last name or both </td>
		<td><input type="text" name="lastName_search" placeholder="enter last name"></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td><input type="submit" name="search" value=<%=Config.SEARCH %> ></td>
	</tr>
</table>

</form>

</div>

<div id="aside" class="responsive-text">
	<%@ include file="../templates/user-aside.jsp" %>
</div>

<%@ include file="../templates/footer.jsp" %>