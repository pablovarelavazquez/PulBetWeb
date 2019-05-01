
<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*"%>
<div id="search-form">

	<form action="<%=ControllerPaths.EVENTO%>" method="post">

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.ACTION)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.BUSCADOR%>"> 
			
		<label><fmt:message
				key="participante" bundle="${messages}" /> </label>
		<c:if test="${not empty participante}">
			<input class="my-little-input" name="participante" type="text" value="${participante}">
		</c:if>
		<c:if test="${empty participante}">
			<input class="my-little-input" name="<%=ParameterNames.PARTICIPANTE%>" type="text">
		</c:if>

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.PARTICIPANTE)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<label><fmt:message key="deporte" bundle="${messages}" /> </label> <select
			class="my-little-input" id="searchdeporte" name="<%=ParameterNames.DEPORTE%>">
			<option></option>
			<c:forEach var="d" items="${deportes}">
				<option value="${d.getIdDeporte()}">${d.getNome()}</option>
			</c:forEach>
		</select>

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.DEPORTE)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<label><fmt:message key="competicion" bundle="${messages}" /></label>
		<select class="my-little-input" id="searchcompeticion" name="<%=ParameterNames.COMPETICION%>">
			<option></option>
			<c:forEach var="c" items="${competiciones}">
				<option value="${c.getIdCompeticion()}">${c.getNome()}</option>
			</c:forEach>

		</select>

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.COMPETICION)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<label><fmt:message key="desde"	bundle="${messages}" /> 
		</label> <input class="my-little-input" name="<%=ParameterNames.FECHA_DESDE%>" type="date">

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.FECHA_DESDE)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<label><fmt:message key="hasta" bundle="${messages}" /> </label> <input
			class="my-little-input" name="<%=ParameterNames.FECHA_HASTA%>" type="date">

		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.FECHA_HASTA)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<input class="my-button" type="submit"
			value="<fmt:message key="find" bundle="${messages}" />">
	</form>
</div>

