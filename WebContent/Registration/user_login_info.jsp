<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>

<% 
String fName = "", lName = "", street = "", city = "", state="", zip="", phone="", email="";
String erMsg ="";
user = (User)session.getAttribute("newUser");

if(user != null){
	fName = user.getFirstName();
	lName = user.getLastName();
	
	Address address;
	if((address = user.getAddress()) != null){			
		street = address.getStreet();
		city = address.getCity();
		state = address.getState();
		zip = address.getZip();
	}
	
	ContactInfo ci;
	if((ci = user.getContactInfo()) != null){
		phone = ci.getPhone();
		email = ci.getEmail();
	}
	
	erMsg = (String)request.getAttribute("erMsg");

}

%>
<div id="content" class="responsive-text">

	<div class="errorMessage align_left center">
		<font><%=(erMsg != null? erMsg: "") %></font>
	</div>

	<div id="criteria" class="greenMessage">
	
		* Password must contains at least 8 characters.<br>
		* Password must not exceed 24 characters.<br>
		* Password must contains at least 2 upper case characters.<br>
		* Password must contains at least 2 lower case characters.<br>
		* Password must contains at least 2 digits.<br>
		* Password must contains at least 2 special characters (!@#$%^()&*).<br><br>
	
	</div>

	<form action="${pageContext.request.contextPath}/UserLoginInfo" method="post">
	
	<table>
	
		<tr>
			<th></th>
			<th>Create credentials</th>
		</tr>
		<tr>
			<td>Username:</td>
			<td><input type="text" name="username" value="" autofocus /></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="password" name="password" value="" /></td>
		</tr>
		<tr>
			<td>Confirm Password:</td>
			<td><input type="password" name="password1" value="" /></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input type="submit" name="action" value=<%=Config.PREVIOUS %> />
				<input type="submit" name="action" value=<%=Config.SUBMIT %> autofocus/>
			</td>
		</tr>
		
	
	</table>
	
		
	</form>


</div><!-- End content -->

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
	
	<strong>Contact</strong>
	<ul>
		<li>Phone: <%=phone %></li>
		<li>Email: <%=email %></li>
	</ul>
	
	
</div><!-- End aside -->

<%@ include file="../templates/footer.jsp" %>