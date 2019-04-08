
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
			
		<input name="evento"
			type="text" placeholder="Id do evento">
		<%
				parameterErrors = errors.showErrors(ParameterNames.EVENTO);
				for (String error: parameterErrors) {
					%><li><%=error%></li>
		<%
				}
			%>

		<input name="competicion" type="text" placeholder="Id da competicion">
		<%
				parameterErrors = errors.showErrors(ParameterNames.COMPETICION);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
		%>
		
		<input name="fecha" type="text" placeholder="Fecha hasta"> 
			<%
				parameterErrors = errors.showErrors(ParameterNames.FECHA_HASTA);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
		
		<input name="deporte" type="text" placeholder="Id do deporte">
					<%
				parameterErrors = errors.showErrors(ParameterNames.DEPORTE);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
		 
		<input name="participante" type="text" placeholder="Participante"> 
			<%
				parameterErrors = errors.showErrors(ParameterNames.PARTICIPANTE);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
		
		<input type="submit" value="Buscar">
	</form>
</div>

