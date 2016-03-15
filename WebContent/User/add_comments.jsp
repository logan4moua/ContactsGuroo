<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>

<%
/* User user = (User) session.getAttribute("user"); */
if(user == null){
	response.sendRedirect(request.getContextPath()+Config.HOME);
}
String fName = "", lName = "", street = "", city = "", state="", zip="", phone_1="", phone_2="";
String comment_1 = "", comment_2="", comment_3="";
Contact contact = (Contact)session.getAttribute("newContact");
if(contact != null){
	fName = contact.getFirstName();
	lName = contact.getLastName();
	
	Address address = contact.getAddress();
	street = address.getStreet();
	city = address.getCity();
	state = address.getState();
	zip = address.getZip();
	
	PhoneCollection pc = contact.getPhones();
	phone_1 = pc.get(0).getPhone();
	phone_2 = pc.get(1).getPhone();
	
	CommentCollection cc = contact.getComments();
	if(cc != null){
		comment_1 = cc.get(0).getComment();
		comment_2 = cc.get(1).getComment();
		comment_3 = cc.get(2).getComment();
	}
	
}

%>
<div id="content" class="responsive-text">
	<h2>Add Comment</h2><br><br>
	<form action="${pageContext.request.contextPath}/FinalizeNewContact" method="post">
		<table>
			<tr>
				<td>Comment 1: </td>
				<td><input type="text" name="comment_1" value="<%=comment_1%>" /></td>
			</tr>
			
			<tr>
				<td>Comment 2: </td>
				<td><input type="text" name="comment_2" value="<%=comment_2%>" /></td>
			</tr>
			<tr>
				<td>Comment 3: </td>
				<td><input type="text" name="comment_3" value="<%=comment_3%>" /></td>
			</tr>
			
			<tr>
				<td></td>
				<td>
				<table>
					<tr>
						<td><input type="submit" name="action" value=<%=Config.PREVIOUS %> /></td>
						<td><input type="submit" name="action" value=<%=Config.CANCEL %> /></td>
					</tr>
					<tr></tr>
					<tr>
						<td></td>
						<td><input type="submit" name="action" value=<%=Config.FINALIZE %> /></td>
					</tr>
					
				</table>
				
				</td>
			</tr>

	
		</table>
	</form>

</div>

<div id="aside" class="responsive-text">
	<strong>Name</strong>
	<ul>
		<li>First Name: <%=fName %></li>
		<li>Last Name: <%=lName %></li>
	</ul>
	
	<strong>Address</strong>
	<ul>
		<li>Street: <%=street %></li>
		<li>City: <%=city %></li>
		<li>State: <%=state %></li>
		<li>Zip: <%=zip %></li>
	</ul>
	
	<strong>Phones</strong>
	<ul>
		<li>Cell phone: <%=phone_1 %></li>
		<li>Home phone: <%=phone_2 %></li>
	</ul>
	
	
</div><!-- End aside -->
<%@ include file="../templates/footer.jsp" %>