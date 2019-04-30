<%@page
	import="java.util.List, com.pulbet.web.util.*, com.pulbet.web.model.*, com.pulbet.web.controller.*, com.pvv.pulbet.model.* "%>
	
<c:set var="acumulada" scope="page" value="${1.0}"/>
<div id="carrito">
	
	
	
	<c:if test="${not empty sessionScope['carrito'] && not empty sessionScope['carrito'].getLineas()}">
	
		<c:forEach var="l" items="${sessionScope['carrito'].getLineas()}">
			<div class="lineacarrito">

		<p>${l.getEvento().getLocal().getNome()} - ${l.getEvento().getVisitante().getNome()}</p>
		<p>${l.getResultado().getNombre()}</p>
		<p class="cuota">${l.getResultado().getCuota()}</p>


		<a class="dellinea" data-evento="${l.getEvento().getIdEvento()}"
			data-resultado="${l.getResultado().getIdResultado()}">X</a>

	<c:set var="acumulada" scope="page" value="${acumulada * l.getResultado().getCuota()}"/>

		<hr>
	</div>
		</c:forEach>
		<form action="<%=ControllerPaths.APUESTA%>">
		<input type="hidden" name="${ParameterNames.ACTION}"
			value="<%=Actions.APOSTAR%>"> 
			
		<label><label><fmt:message key="acumulada" bundle="${messages}" /> </label></label>
		<input type="text" value="${acumulada}" id="acumulada" disabled>
		<br> 
		
		<label><label><fmt:message key="importe" bundle="${messages}" /> </label></label> 
		<input id="importe"
			name="<%=ParameterNames.IMPORTE%>" type="text" value="1.0" required>
		<br> 
		
		<label><label><fmt:message key="ganancias" bundle="${messages}" /> </label></label> 
		<input type="text"
			name="<%=ParameterNames.GANANCIAS%>" id="ganancias" disabled>
		<br>
		<button type="submit"><label><fmt:message key="apostar" bundle="${messages}" /> </label></button>
	</form>
	</c:if>


</div>