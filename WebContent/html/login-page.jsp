<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*"%>

<%@include file="/html/common/header.jsp"%>
<div id="login-form">

	<div class="contenedor-header">
	<h3>
		<fmt:message key="welcome" bundle="${messages}" />
	</h3>
	</div>
	
	
	<div class="contenedor-grey">
	
	<div class="green">
				<h2>
					Login
				</h2>
	</div>
	
	<form action="<%=ControllerPaths.USUARIO%>" method="post">

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.ACTION)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.LOGIN%>" />
			
		<label><fmt:message key="email" bundle="${messages}" /></label>

		<% 
					if(cookie!=null){
					String email = cookie.getValue();
					%>
		<input class="my-input" type="email" name="<%=ParameterNames.LOGIN_EMAIL%>"
		value="<%=email%>" />
		<% 
					} else {
					%>
		<input class="my-input" type="email" name="<%=ParameterNames.LOGIN_EMAIL%>"
			value="<%=ParamsUtils.getParameter(request, ParameterNames.LOGIN_EMAIL)%>" />
		<% 
					}
					%>

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.LOGIN_EMAIL)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>
		
		<label>
				<fmt:message key="password" bundle="${messages}" />
		</label>

		<input class="my-input" type="password" name="<%=ParameterNames.PASSWORD%>" />

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.PASSWORD)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>
		
		<br>

		<input class="my-input" type="submit" value="<fmt:message key="enter" bundle="${messages}"/>" />
	</form>
	</div>

	
</div>

<%@include file="/html/common/footer.jsp"%>