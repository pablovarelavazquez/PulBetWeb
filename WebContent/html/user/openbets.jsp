<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.*, com.pulbet.web.util.* , com.pvv.pulbet.service.*"%>

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
		<p class="fechaapuesta"><%=DateUtils.WITH_HOUR_FORMAT.format(a.getFecha())%></p>
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
			<%=(a.getGanancias()/a.getImporte())%></p>
		<p>
			Importe:
			<%=a.getImporte()%></p>

		<%
			
						if(a.getProcesado() ==  0){
							%>
		<p>Estado: <p class="pendiente">PENDIENTE</p></p>

		<%
			
							}
							if(a.getProcesado() ==  1){
							%>
		<p>Estado: <p class="acertada">ACERTADA</p></p>

		<%
			
							}
							if(a.getProcesado() ==  2){
							%>
		<p>Estado: <p class="fallada">FALLADA</p></p>

		<%
			
							}
							%>
		<div class="masdetalle" data-id="<%=a.getIdApuesta()%>">
			<p>Detalles</p>
			<div class="fillo"></div>
		</div>
		
	</div>

	<%
				} else { 
					%>
	<div class="apuestahistorial">
		<p class="fechaapuesta"><%=DateUtils.WITH_HOUR_FORMAT.format(a.getFecha())%></p>
		<p class="idapuesta">
			Id Apuesta:
			<%=a.getIdApuesta()%></p>
		<p>
			 Simple
		</p>
		<p>
			Cuotas:
			<%=(a.getGanancias()/a.getImporte())%></p>
		<p>
			Importe:
			<%=a.getImporte()%></p>

		<%
			
							if(a.getProcesado() ==  0){
							%>
		<p>Estado: <p class="pendiente">PENDIENTE</p></p>

		<%
			
							}
							if(a.getProcesado() ==  1){
							%>
		<p>Estado: <p class="acertada">ACERTADA</p></p>

		<%
			
							}
							if(a.getProcesado() ==  2){
							%>
		<p>Estado: <p class="fallada">FALLADA</p></p>

		<%
			
							}
							%>
		<div class="masdetalle" data-id="<%=a.getIdApuesta()%>">
			<p>Detalles</p>
			<div class="fillo"></div>
		</div>

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