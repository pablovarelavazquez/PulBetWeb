<%@page
	import="java.util.List, com.pulbet.web.util.*, com.pulbet.web.model.*, com.pulbet.web.controller.*, com.pvv.pulbet.model.* "%>
	
<c:set var="acumulada" scope="page" value="${1.0}"/>
<div id="carrito">
	
	
	
	<c:if test="${not empty sessionScope['carrito'] && not empty sessionScope['carrito'].getLineas()}">
	
		<c:forEach var="l" items="${sessionScope['carrito'].getLineas()}">
			<div class="lineacarrito">

		<p>${l.getEvento().getLocal().getNome()} - ${l.getEvento().getVisitante().getNome()}</p>
		<p>${l.getResultado().getNombre()}</p>
		<p class="cuotacarrito">${l.getResultado().getCuota()}</p>


		<a class="dellinea" data-evento="${l.getEvento().getIdEvento()}"
			data-resultado="${l.getResultado().getIdResultado()}">X</a>

	<c:set var="acumulada" scope="page" value="${acumulada * l.getResultado().getCuota()}"/>

		<hr>
	</div>
		</c:forEach>
		
		<div class="contenedor-carrito">
		<form action="<%=ControllerPaths.APUESTA%>">
		<input type="hidden" name="${ParameterNames.ACTION}"
			value="<%=Actions.APOSTAR%>"> 
			
		<div class="cont">
		<label><fmt:message key="acumulada" bundle="${messages}" /></label>
		<input class="my-little-input" type="text" value="${acumulada}" id="acumulada" disabled>
		</div>
		<br> 
		
		<div class="cont">
		<label><label><fmt:message key="importe" bundle="${messages}" /> </label></label> 
		<input class="my-little-input" id="importe"
			name="<%=ParameterNames.IMPORTE%>" type="text" value="1.0" required>
		</div>
		<br> 
		
		<div class="cont">
		<label><fmt:message key="ganancias" bundle="${messages}" /></label> 
		<input class="my-little-input" type="text"
			name="<%=ParameterNames.GANANCIAS%>" id="ganancias" disabled>
		</div>	
		<br>
		<input class="carrito-bttn" type="submit" value="<fmt:message key="apostar" bundle="${messages}" />">
	</form>
	</div>
	</c:if>


</div>