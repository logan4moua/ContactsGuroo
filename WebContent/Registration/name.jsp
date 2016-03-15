<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>

<%
String fName = "", lName = "", erMsg = "";
User newUser = (User) session.getAttribute("newUser");
if(newUser != null){
	fName = newUser.getFirstName();
	lName = newUser.getLastName();
	
	erMsg = (String)request.getAttribute(Config.errorMsg);
}
else{
	response.sendRedirect(request.getContextPath()+Config.HOME);
}

%>
<div id="content" class="responsive-text">

	<div class="errorMessage align_left center">
		<font><%=(erMsg != null? erMsg: "") %></font>
	</div>

	<form action="Name" method="post">
	<table >
		<tr>
			<th></th>
			<th>Enter your name</th>
		</tr>
		<tr>
			<td>First Name:</td>
			<td><input type="text" name="firstName" value="<%=fName %>" autofocus/></td>
		</tr>
		<tr>
			<td>Last Name:</td>
			<td><input type="text" name="lastName" value="<%=lName %>" /></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input type="submit" name="action" value=<%=Config.PREVIOUS %> />
				<input type="submit" name="action" value=<%=Config.NEXT %> />
			</td>
		</tr>
	
	</table>
		
	</form>

</div><!-- End content -->


<%@ include file="../templates/footer.jsp" %>