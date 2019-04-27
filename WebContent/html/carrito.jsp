<%@page
	import="java.util.List, com.pulbet.web.util.*, com.pulbet.web.model.*, com.pulbet.web.controller.*, com.pvv.pulbet.model.* "%>
<div id="carrito">

	<%
	Carrito c = (Carrito) SessionManager.get(request, SessionAttributeNames.CARRITO);
	Double acumulada = 1.0;

	if(c != null) {
		if ((c.getLineas() != null) && (!c.getLineas().isEmpty())){
			
			for (LineaCarrito l : c.getLineas()){
			
%>
	<div class="lineacarrito">

		<p><%=l.getEvento().getLocal().getNome()%>
			-
			<%=l.getEvento().getVisitante().getNome()%></p>
		<p><%=l.getResultado().getNombre()%></p>
		<p class="cuota"><%=l.getResultado().getCuota()%></p>


		<a class="dellinea" data-evento="<%=l.getEvento().getIdEvento()%>" data-resultado="<%=l.getResultado().getIdResultado()%>">X</a>

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
			<input type="text" value="<%=acumulada%>"  id="acumulada" disabled>
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
	
	

%>


</div>