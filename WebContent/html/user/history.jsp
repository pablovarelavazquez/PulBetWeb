<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.*, com.pulbet.web.util.* , com.pvv.pulbet.service.*"%>

<div id="history">

	<%
		List<Apuesta> apuestas = (List<Apuesta>) request.getAttribute(AttributeNames.RESULTADOS);

		if (apuestas != null) {
			if (apuestas.isEmpty()) {
	%>
	<p>No se han encontrado resultados para esos criterios de busqueda.
		Pruebe a modificarlos</p>
	<%
		}
		}

		if (apuestas == null) {
	%>
	<p>Introduzca los criterios para la busqueda de resultados</p>
	<%
		}
	%>


	<c:if test="${not empty resultados}">
		<c:forEach var="a" items="${resultados}">

			<div class="apuestahistorial">
				<p class="fechaapuesta">${DateUtils.WITH_HOUR_FORMAT.format(a.getFecha())}</p>
				<p class="idapuesta">Id Apuesta: ${a.getIdApuesta()}</p>
				<c:if test="${a.getLineas().size() > 1}">
					<p>Combinada: ${a.getLineas().size()} eventos</p>
				</c:if>
				<c:if test="${a.getLineas().size() <= 1}">
					<p>Simple</p>
				</c:if>
				<p>Cuotas: ${a.getGanancias() / a.getImporte()}</p>
				<p>Importe: ${a.getImporte()}</p>
				<c:if test="${a.getProcesado() == 1}">
					<div class="acertada">
						<p>Ganancias: ${a.getGanancias()}</p>
					</div>
				</c:if>
				<c:if test="${a.getProcesado() != 1}">
					<div class="fallada">
						<p>Ganancias: 0.00</p>
					</div>
				</c:if>
				<div class="masdetalle" data-id="${a.getIdApuesta()}">
					<p>Detalles</p>
					<div class="fillo"></div>
				</div>
			</div>
		</c:forEach>



		<!-- Paxinacion -->
		<p>
		<center>

			<!-- A la anterior pagina -->
			<c:if test="${page > 1}">
				<a href="${url}&page=${page - 1}"> <fmt:message key="Anterior"
						bundle="${messages}" />
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
				<a href="${url}&page=${page + 1}"> <fmt:message key="siguiente"
						bundle="${messages}" />
				</a>
			</c:if>
		</center>
	</p>
	</c:if>

</div>
