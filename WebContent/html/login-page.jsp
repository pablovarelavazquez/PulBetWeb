<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*"%>

<%@include file="/html/common/header.jsp"%>
<div id="login-form">
	<h3>Bienvenido de nuevo a PULBET</h3>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<%
				List<String> parameterErrors = errors.showErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%>
					<li><%=error%></li>
		<%
				}
			%>
		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.LOGIN%>" />

		<% 
					if(cookie!=null){
					String email = cookie.getValue();
					%>
		<input type="<%=ParameterNames.LOGIN_EMAIL%>"
			name="<%=ParameterNames.LOGIN_EMAIL%>"
			placeholder="usuario@ejemplo.com" value="<%=email%>" />
		<% 
					} else {
					%>
		<input type="email"
			name="<%=ParameterNames.LOGIN_EMAIL%>" placeholder="usuario@ejemplo.com"
			value="<%=ParamsUtils.getParameter(request, ParameterNames.LOGIN_EMAIL)%>" />
		<%
					}
					parameterErrors = errors.showErrors(ParameterNames.LOGIN_EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li>
		<%
					}
				%>


		<input type="password" name="<%=ParameterNames.PASSWORD%>" /> <input
			type="submit" value="Entrar" />
	</form>

	<a href="" id="recuperar">Recuperar contrase�a</a>
</div>

<%@include file="/html/common/footer.jsp"%>