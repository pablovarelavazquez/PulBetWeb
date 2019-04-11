
<%@ page import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*"%>

<div id="login-form">
	<h3>Busca eventos</h3>
	

	<form action="<%=ControllerPaths.EVENTO%>" method="post">
		<%
				List<String> parameterErrors = errors.showErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%><li><%=error%></li>
		<%
				}
			%>

		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.BUSCADOR%>"> 
			
	
		<input name="fecha" type="text" placeholder="Fecha hasta"> 
			<%
				parameterErrors = errors.showErrors(ParameterNames.FECHA_HASTA);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
		
		<input type="submit" value="Buscar">
	</form>
</div>

