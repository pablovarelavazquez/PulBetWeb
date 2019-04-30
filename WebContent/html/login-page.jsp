<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*"%>

<%@include file="/html/common/header.jsp"%>
<div id="login-form">
	<h3><fmt:message key="welcome" bundle="${messages}" /></h3>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.ACTION)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.LOGIN%>" />
			
		<legend>
				<fmt:message key="email" bundle="${messages}" />
		</legend>

		<% 
					if(cookie!=null){
					String email = cookie.getValue();
					%>
		<input type="email" name="<%=ParameterNames.LOGIN_EMAIL%>"
		value="<%=email%>" />
		<% 
					} else {
					%>
		<input type="email" name="<%=ParameterNames.LOGIN_EMAIL%>"
			value="<%=ParamsUtils.getParameter(request, ParameterNames.LOGIN_EMAIL)%>" />
		<% 
					}
					%>

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.LOGIN_EMAIL)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>
		
		<legend>
				<fmt:message key="password" bundle="${messages}" />
		</legend>

		<input type="password" name="<%=ParameterNames.PASSWORD%>" />

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.PASSWORD)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<input type="submit" value="<fmt:message key="enter" bundle="${messages}"/>" />
	</form>

	<a href="" id="recuperar"><fmt:message key="recpassword" bundle="${messages}" /></a>
</div>

<%@include file="/html/common/footer.jsp"%>