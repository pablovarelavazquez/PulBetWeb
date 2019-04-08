<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*"%>

<%@include file="/html/common/header.jsp"%>

<%
	Results<Apuesta> apuestas = (Results<Apuesta>) request.getAttribute(AttributeNames.APUESTAS);

	if (apuestas.getPage().isEmpty()) {
%>
<div id="history">
<p>No tienes apuestas abiertas en este momento.</p>
</div>
<%
	} else {
%>

<div id="history">
	<%
			for (Apuesta a : apuestas.getPage()) {	
				if(a.getLineas().size() > 1){
					%>
	<div class="apuestahistorial">
		<p class="fechaapuesta"><%=a.getFecha()%></p>
		<p class="idapuesta">Id Apuesta: <%=a.getIdApuesta()%></p>
		<p>Simple</p>
		<p>
			Cuotas:
			<%=String.format("%.2f", a.getGanancias()/a.getImporte())%></p>
		<p>
			Importe:
			<%=a.getImporte()%></p>
			
			<p class="masdetalles">Mas detalles</p>
	</div>

	<%
				} else { 
					%>
					<div class="apuestahistorial">
						<p class="fechaapuesta"><%=a.getFecha()%></p>
						<p class="idapuesta">Id Apuesta: <%=a.getIdApuesta()%></p>
						<p>Combinada: <%=a.getLineas().size()%> eventos</p>
						<p>
							Cuotas:
							<%=String.format("%.2f", a.getGanancias()/a.getImporte())%></p>
						<p>
							Importe:
							<%=a.getImporte()%></p>
							
						<p class="masdetalles">Mas detalles</p>
					</div>

					<%	
				}

			}
%>
</div>
<%
	}
%>
<%@include file="/html/common/footer.jsp"%>