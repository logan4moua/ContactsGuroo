
<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>
<%
/* user = (User) session.getAttribute("newUser"); */
if(user == null){
	response.sendRedirect(request.getContextPath()+Config.HOME);
}
String successMsg = (String)request.getAttribute("successMsg");

%>
<div id="content" class="responsive-text">
	<div class="greenMessage">
		<%=successMsg != null? successMsg: ""%>
	</div>
	<br>
	<h2>Manage Contacts</h2> <br><br>
	<table id="contacts-table" class="data-table">
		<thead>
			<th>First Name</th>
			<th>Last Name</th>
			<th>View Details</th>
			<th>Edit</th>
			<th>Delete</th>
		</thead>
		<tbody>
	<%
	AddressBook ab = (AddressBook) session.getAttribute("addressBook");
	if(ab != null){
		for(Contact contact: ab.getContactList()){
	%>

				<tr>
					<td><%=contact.getFirstName() %></td>
					<td><%=contact.getLastName() %></td>
					<td>
						<form action="${pageContext.request.contextPath}/ViewContactDetails" method="post">
							<input type="hidden" name="c_id" value=<%=contact.getC_id() %> />
							<input type="submit" name="submit" value="details" />
						</form>
					</td>
					<td>
						<form action="${pageContext.request.contextPath}/EditContact" method="post">
							<input type="hidden" name="c_id" value=<%=contact.getC_id() %> />
							<input type="submit" name="submit" value="Edit" />
						</form>
					</td>
					<td>
						<form action="${pageContext.request.contextPath}/DeleteContact" method="post">
							<input type="hidden" name="c_id" value=<%=contact.getC_id() %> />
							<input type="submit" name="submit" value="Delete" />
						</form>
					</td>
				</tr>

			
	<% }
	}
	%>
		</tbody>
	</table>
</div>

<div id="aside" class="responsive-text">
	<%@ include file="../templates/user-aside.jsp" %>
</div>
<%@ include file="../templates/footer.jsp" %>