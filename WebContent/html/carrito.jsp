<%@page
	import="java.util.List, com.pulbet.web.util.*, com.pulbet.web.model.*, com.pulbet.web.controller.* "%>
<div id="carrito">

	<%
	Carrito c = (Carrito) SessionManager.get(request, SessionAttributeNames.CARRITO);
	List<String> apuestaErrors = errors.showErrors(ParameterNames.APUESTA);
	Double acumulada = 1.0;

	if (c != null) {
		if ((c.getLineas() != null) && (!c.getLineas().isEmpty())){
			
			for (LineaCarrito l : c.getLineas()){
			
%>
	<div class="lineacarrito">

		<p><%=l.getEvento().getLocal().getNome()%>
			-
			<%=l.getEvento().getVisitante().getNome()%></p>
		<p><%=l.getResultado().getNombre()%></p>
		<p class="cuota"><%=l.getResultado().getCuota()%></p>

		<a
			href="<%=(ControllerPaths.CARRITO + "?")%>
				<%=ParameterNames.ACTION%>=<%=Actions.DEL_CARRITO%>&amp;<%=ParameterNames.ID_EVENTO%>=<%=l.getEvento().getIdEvento()%>
				&amp;<%=ParameterNames.ID_RESULTADO%>=<%=l.getResultado().getIdResultado()%>">X</a>

		<hr>

	</div>
	<% 
				acumulada = acumulada * l.getResultado().getCuota();
			}
%>
	
	<form action="<%=ControllerPaths.APUESTA%>">
		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.APOSTAR%>"> 
			<label>Cuota acumulada</label>
			<input type="text" value="<%=String.format("%.2f", acumulada)%>"  id="acumulada" disabled>
			<br> 
			<label>Importe</label> 
			<input id="importe" name="<%=ParameterNames.IMPORTE%>" type="text" value="1.0" required>
			<br> 
			<label>Ganancias</label> 
			<input
			type="text" name="<%=ParameterNames.GANANCIAS%>" id="ganancias" disabled> <br>
		<button type="submit">Apostar</button>
	</form>

	<% 
		}
	} 
	
	
	for (String error: apuestaErrors) {
		%><li><%=error%></li><%
	}


%>


</div>