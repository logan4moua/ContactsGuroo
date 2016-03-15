
<%@ include file="templates/header.jsp" %>
<%@ include file="templates/topbar.jsp" %>

<%

String signin_error = (String) request.getAttribute(Config.errorMsg);
String successMsg = (String) request.getAttribute(Config.successMsg);

%>

<div id="content" class="responsive-text">
	<div class="greenMessage">
		<%=successMsg == null? "": successMsg %>
	</div>
	
	<%if(session.getAttribute("user") != null | session.getAttribute("admin") != null){%>
	<%@ include file="Others/home_context.jsp" %>
	<%}else{ %>
	<%@ include file="Registration/user_signup.jsp" %>
	<%} %>


</div><!-- End content -->

<div id="aside" class="responsive-text">
	<div class="errorMessage">
		<%=signin_error == null ? "": signin_error%>
	</div>

	<%if(session.getAttribute("user") != null){%>
	<%@ include file="templates/user-aside.jsp" %>
	<%}else if(session.getAttribute("admin") != null){ %>
	<%@ include file="templates/admin-aside.jsp" %>
	<%}else{ %>
	<%@ include file="User/user_login_form.jsp" %>
	<%} %>
	
</div>


<%@ include file="templates/footer.jsp" %>
