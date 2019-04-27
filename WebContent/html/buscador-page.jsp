
<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*"%>
<div id="search-form">
	<form action="<%=ControllerPaths.EVENTO%>" method="post">
		<%
			List<String> parameterErrors = errors.showErrors(ParameterNames.ACTION);
			for (String error : parameterErrors) {
		%><li><%=error%></li>
		<%
			}
		%>

		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.BUSCADOR%>">

		<label>Participante: </label>
		<c:if test="${not empty participante}">
			<input name="participante" type="text" placeholder="Participante"
				value="${participante}">
		</c:if>
		<c:if test="${empty participante}">
			<input name="participante" type="text" placeholder="Participante">
		</c:if>
		<%
			parameterErrors = errors.showErrors(ParameterNames.PARTICIPANTE);
			for (String error : parameterErrors) {
		%><li><%=error%></li>
		<%
			}
		%>
<label>Deporte: </label>
		<select id="searchdeporte" name="deporte">
					<option></option>
			<c:forEach var="d" items="${deportes}">
					<option value="${d.getIdDeporte()}">${d.getNome()}</option>
			</c:forEach>
		</select> 
<label>Competicion: </label>
		<select id="searchcompeticion" name="competicion">
			<option></option>
			<c:forEach var="c" items="${competiciones}">
				<option value="${c.getIdCompeticion()}">${c.getNome()}</option>
			</c:forEach>

		</select> 
		
		<label>Fecha desde: </label>
		<input name="fechadesde" type="date">
		<%
			parameterErrors = errors.showErrors(ParameterNames.FECHA_DESDE);
				for (String error : parameterErrors) {
		%><li><%=error%></li>
		<%
			}
		%>
		
		<label>Fecha hasta: </label>
		<input name="fechahasta" type="date">
		<%
			parameterErrors = errors.showErrors(ParameterNames.FECHA_HASTA);
				for (String error : parameterErrors) {
		%><li><%=error%></li>
		<%
			}
		%>

		<input type="submit" value="Buscar">
	</form>
</div>

