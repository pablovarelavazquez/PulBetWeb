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
<!-- Paxinacion -->
		<p>
		<center>

			<!-- A la anterior pagina -->
			<c:if test="${page > 1}">
				<a href="${url}&page=${page - 1}"> <fmt:message
						key="Anterior" bundle="${messages}" />
				</a>
			&nbsp;&nbsp;
			</c:if>

			<c:if test="${totalPages > 1}">

				<c:if test="${firstPagedPage > 2}">
					<a href="${url}&page=1"><b>1</b></a>
					<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b>
				</c:if>

				<c:forEach begin="${firstPagedPage}" end="${lastPagedPage}" var="i">
					<c:choose>
						<c:when test="${page != i}">
					&nbsp;<a href="${url}&page=${i}"><b>${i}</b></a>&nbsp;
			  </c:when>
						<c:otherwise>
					&nbsp;<b>${i}</b>&nbsp;
			  </c:otherwise>
					</c:choose>
				</c:forEach>

				<c:if test="${lastPagedPage < totalPages-1}">
					<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b>
					<a href="${url}&page=${totalPages}"><b>${totalPages}</b></a>
				</c:if>

			</c:if>

			<!-- A la siguiente página -->
			<c:if test="${page < totalPages}">
				&nbsp;&nbsp;		
				<a href="${url}&page=${page + 1}"> <fmt:message
						key="siguiente" bundle="${messages}" />
				</a>
			</c:if>
			</center></p>

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
