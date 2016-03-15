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
		<td>last name or both</td>
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
<br>
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
	AddressBook ab = (AddressBook) request.getAttribute("found");
	if(ab != null){
		for(Contact contact: ab.getContactList()){
	%>

				<tr>
					<td><%=contact.getFirstName() %></td>
					<td><%=contact.getLastName() %></td>
					<td>
						<form action="ViewContactDetails" method="post">
							<input type="hidden" name="c_id" value=<%=contact.getC_id() %> />
							<input type="submit" name="submit" value="details" />
						</form>
					</td>
					<td>
						<form action="EditContact" method="post">
							<input type="hidden" name="c_id" value=<%=contact.getC_id() %> />
							<input type="submit" name="submit" value="Edit" />
						</form>
					</td>
					<td>
						<form action="DeleteContact" method="post">
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