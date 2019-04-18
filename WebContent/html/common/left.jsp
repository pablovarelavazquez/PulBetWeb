<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*"%>
	
<div id="left">

<div id="participanteseacrh">

	<%
		String participante = (String) request.getAttribute(ParameterNames.PARTICIPANTE);
	%>

	<form action="<%=ControllerPaths.EVENTO%>" method="post">
		<%
			List<String> errores = errors.showErrors(ParameterNames.ACTION);
			for (String error : errores) {
		%><li><%=error%></li>
		<%
			}
		%>

		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.BUSCADOR%>">

		<%
			if (participante != null) {
		%>
		<input name="participante" type="text" placeholder="Participante"
			value="<%=participante%>">
		<%
			} else {
		%>
		<input name="participante" type="text" placeholder="Participante">
		<%
			}
			errores = errors.showErrors(ParameterNames.PARTICIPANTE);
			for (String error : errores) {
		%><li><%=error%></li>
		<%
			}
		%>
		<button type="submit">Buscar</button>
		</form>
</div>

<div id="sports">

	<c:if test="${not empty deportes}">
		<ul>
			<c:forEach items="${deportes}" var="d">
			
			<c:url var="urlComp" scope="page" value="evento">
					<c:param name="action" value="<%=Actions.FIND_COMPETITION%>"/>
					<c:param name="id" value="${d.idDeporte}" />
			</c:url>
			
				<li><a href="${urlComp}">${d.getNome()}</a></li>
			</c:forEach>
		</ul>
	</c:if>

</div>
</div>