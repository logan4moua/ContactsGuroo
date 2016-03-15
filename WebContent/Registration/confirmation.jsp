<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>

<% 

String fName = "", lName = "", street = "", city = "", state="", zip="", phone="", email="";

if( user != null ){
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

}

%>

<div id="content" class="responsive-text">

	<p class="greenMessage">Congratulation! <%=fName %> . Your account has been created</p><br>
	
	<form action="${pageContext.request.contextPath}/manageContact" method="post">
		<input type="submit" name="submit" value="Start Manage Contacts" />
	</form>

</div> <!-- End content -->

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