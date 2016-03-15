
	<form action="${pageContext.request.contextPath}/ViewAllUsers" method="post">
	<input type="submit" name="submit" value="View All Users" />
	</form>
	<br>
	
	<form action="${pageContext.request.contextPath}/Administrator/add_new_user.jsp" method="post">
		<input type="submit" name="submit" value="Add New User" />
	</form>
	<br>
	
	<form action="${pageContext.request.contextPath}/ViewAllAdmins" method="post">
		<input type="submit" name="submit" value="View All Admins" />
	</form>
	<br>
	
	<form action="${pageContext.request.contextPath}/Administrator/add_new_admin.jsp" method="post">
		<input type="submit" name="submit" value="Add New Admin" />
	</form>
	<br>
	
	<form action="${pageContext.request.contextPath}/DatabaseBackup" method="post">
		<input type="submit" name="submit" value="Backup Database" />
	</form>
	<br>
	
	<form action="${pageContext.request.contextPath}/Logout" method="post" >
		<input type="submit" name="logout" value="LOGOUT" />
	</form>
