<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*"%>

<%
	Results<Apuesta> apuestas = (Results<Apuesta>) request.getAttribute(AttributeNames.APUESTAS);

	if (apuestas != null) {

		if (apuestas.getPage().isEmpty()) {
%>
<div id="history">
<p>No hemos encontrado apuestas para esos criterios. Pruebe a
	modificar los criterios.</p>
</div>
<%
	} else {
%>



<div id="history">
	<%
		for (Apuesta a : apuestas.getPage()) {
					if (a.getLineas().size() > 1) {
	%>
	<div class="apuestahistorial">
		<p class="fechaapuesta"><%=a.getFecha()%></p>
		<p class="idapuesta">
			Id Apuesta:
			<%=a.getIdApuesta()%></p>
		<p>Simple</p>
		<p>
			Cuotas:
			<%=String.format("%.2f", a.getGanancias()/a.getImporte())%></p>

		<p>
			Importe:
			<%=a.getImporte()%></p>
		<%
			if (a.getProcesado() == 1) {
		%>
		<div class="acertada">
			<p>
				Ganancias:
				<%=a.getGanancias()%></p>
		</div>
		<%
			} else {
		%>
		<div class="fallada">
			<p>Ganancias: 0.00</p>
		</div>
		<%
			}
		%>


		<p class="masdetalles">Mas detalles</p>
	</div>

	<%
		} else {
	%>
	<div class="apuestahistorial">
		<p class="fechaapuesta"><%=a.getFecha()%></p>
		<p class="idapuesta">
			Id Apuesta:
			<%=a.getIdApuesta()%></p>
		<p>
			Combinada:
			<%=a.getLineas().size()%>
			eventos
		</p>

		<p>
			Cuotas:
			<%=String.format("%.2f", a.getGanancias()/a.getImporte())%></p>

		<p>
			Importe:
			<%=a.getImporte()%></p>
		<%
			if (a.getProcesado() == 1) {
		%>
		<div class="acertada">
			<p>
				Ganancias:
				<%=a.getGanancias()%></p>
		</div>
		<%
			} else {
		%>
		<div class="fallada">
			<p>Ganancias: 0.00</p>
		</div>
		<%
			}
		%>



		<p class="masdetalles">Mas detalles</p>
	</div>

	<%
		}

				}
	%>
</div>

<%
	}
	} else {
%>
<div id="history">
	<p>Seleccione los criterios para la busqueda.</p>
</div>
<%
	}
%>
