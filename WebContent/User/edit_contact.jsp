<%@ include file="../templates/header.jsp" %>
<%@ include file="../templates/topbar.jsp" %>
<%
/* User user = (User) session.getAttribute("user"); */
if(user == null){
	response.sendRedirect(request.getContextPath()+Config.HOME);
}

String fName = "", lName = "", street = "", city = "", state="", zip="", phone_1="", phone_2="";
String comment_1 = "", comment_2 ="", comment_3 ="";
Contact contact = (Contact)session.getAttribute("editingContact");
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
	comment_1 = cc.get(0).getComment();
	comment_2 = cc.get(1).getComment();
	comment_3 = cc.get(2).getComment();
}

%>
<div id="content" class="responsive-text">
	<h2>Edit Contact</h2><br><br>
	<form action="${pageContext.request.contextPath}/ConfirmContactEditing" method="post">
		<table>
			<tr>
				<td>First Name: </td>
				<td><input type="text" name="firstName" value="<%=fName %>" ></td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><input type="text" name="lastName" value="<%=lName %>" ></td>
			</tr>
			<tr>
			 <td></td>
			 <th>Address</th>
			</tr>
			<tr>
				<td>Street: </td>
				<td><input type="text" name="street" value="<%=street %>" ></td>
			</tr>
			<tr>
				<td>City: </td>
				<td><input type="text" name="city" value="<%=city %>" ></td>
			</tr>
			
			<tr>
				<td>State: </td>
				<td>
				<select name="state">
					<option value="<%=state %>" selected><%=state %></option>
					<option value="">US STATES</option>
					<option value="AL">AL</option>
					<option value="AK">AK</option>
					<option value="AZ">AZ</option>
					<option value="AR">AR</option>
					<option value="CA">CA</option>
					<option value="CO">CO</option>
					<option value="CT">CT</option>
					<option value="DE">DE</option>
					<option value="FL">FL</option>
					<option value="GA">GA</option>
					<option value="HI">HI</option>
					<option value="ID">ID</option>
					<option value="IL">IL</option>
					<option value="IN">IN</option>
					<option value="IA">IA</option>
					<option value="KS">KS</option>
					<option value="KY">KY</option>
					<option value="LA">LA</option>
					<option value="ME">ME</option>
					<option value="MD">MD</option>
					<option value="MA">MA</option>
					<option value="MI">MI</option>
					<option value="MN">MN</option>
					<option value="MS">MS</option>
					<option value="MO">MO</option>				
					<option value="MT">MT</option>
					<option value="NE">NE</option>
					<option value="NV">NV</option>
					<option value="NH">NH</option>
					<option value="NJ">NJ</option>
					<option value="NM">NM</option>				
					<option value="NY">NY</option>
					<option value="NC">NC</option>
					<option value="ND">ND</option>
					<option value="OH">OH</option>
					<option value="OK">OK</option>
					<option value="OR">OR</option>
					<option value="PA">PA</option>
					<option value="RI">RI</option>
					<option value="SC">SC</option>
					<option value="SD">SD</option>
					<option value="TN">TN</option>
					<option value="TX">TX</option>
					<option value="UT">UT</option>
					<option value="VT">VT</option>
					<option value="VA">VA</option>
					<option value="WA">WA</option>
					<option value="WV">WV</option>
					<option value="WI">WI</option>
					<option value="WY">WY</option>
					<option value="">US TERRITORY</option>
					<option value="AS">AS</option>
					<option value="DC">DC</option>
					<option value="FM">FM</option>
					<option value="GU">GU</option>				
					<option value="MH">MH</option>
					<option value="MP">MP</option>
					<option value="PW">PW</option>
					<option value="PR">PR</option>
					<option value="VI">VI</option>
					<option value="OT">OTHERS</option>
				</select>
				</td>
			</tr>
			<tr>
				<td>Zip code: </td>
				<td><input type="number" name="zip" value=<%=zip %> ></td>
			</tr>
			<tr>
			 <td></td>
			 <th>Phones</th>
			</tr>
			<tr>
				<td>Cell phone: </td>
				<td><input type="text" name="phone_1" value="<%=phone_1 %>" ></td>
			</tr>
			<tr>
				<td>Home phone: </td>
				<td><input type="text" name="phone_2" value="<%=phone_2 %>" ></td>
			</tr>
			<tr>
			 <td></td>
			 <th>Comments</th>
			</tr>
			<tr>
				<td>Comment 1: </td>
				<td><input type="text" name="comment_1" value="<%=comment_1 %>" ></td>
			</tr>
			
			<tr>
				<td>Comment 2: </td>
				<td><input type="text" name="comment_2" value="<%=comment_2 %>" ></td>
			</tr>
			<tr>
				<td>Comment 3: </td>
				<td><input type="text" name="comment_3" value="<%=comment_3 %>" ></td>
			</tr>
			<tr>
			 <td></td>
			 <td></td>
			</tr>
			<tr>
				<td></td>
				<td>
				<table>
					<tr>
						<td><input type="submit" name="action" value=<%=Config.CANCEL %> ></td>
						<td><input type="submit" name="action" value=<%=Config.CONFIRM %> ></td>
					
					</tr>
				
				</table>
				</td>
			</tr>
	
		</table>

	</form>

</div>

<div id="aside" class="responsive-text">
	<%@ include file="../templates/user-aside.jsp" %>
</div>
<%@ include file="../templates/footer.jsp" %>