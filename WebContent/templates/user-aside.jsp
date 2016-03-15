
	<form action="${pageContext.request.contextPath}/User/manage_contacts.jsp" method="post">
	<input type="submit" name="submit" value="Manage Contacts" />
	</form>
	<br>
	<form action="${pageContext.request.contextPath}/User/add_new_contact.jsp" method="post">
		<input type="submit" name="submit" value="Add New Contact" />
	</form>
	<br>
	<form action="${pageContext.request.contextPath}/ViewMyProfile" method="post">
		<input type="submit" name="submit" value="My Profile" />
	</form>
	<br>
	<form action="${pageContext.request.contextPath}/Logout" method="post" >
		<input type="submit" name="logout" value="LOGOUT" />
	</form>
