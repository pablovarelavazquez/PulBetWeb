<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*"%>

<div id="results">

	<c:if test="${(not empty eventos) && (empty resultados)}">
		<c:if test="${not empty eventos.getPage()}">
			<ul>
				<c:forEach var="e" items="${eventos.getPage()}">


					<c:url var="urlDetalle" scope="page" value="evento">
						<c:param name="action" value="<%=Actions.FIND_DETAIL%>" />
						<c:param name="id" value="${e.idEvento}" />
					</c:url>
					<li><a href="${urlDetalle}">${e.getLocal().getNome()} vs
							${e.getVisitante().getNome()} - ${e.getFecha()}</a></li>


				</c:forEach>
			</ul>
		</c:if>
	</c:if>

	

	<c:if test="${not empty resultados}">
			<p><fmt:message key="resultados" bundle="${messages}"/></p>
		
		<ul>
			<c:forEach var="r" items="${resultados}">


				<c:url var="urlDetalle" scope="page" value="evento">
					<c:param name="action" value="<%=Actions.FIND_DETAIL%>" />
					<c:param name="id" value="${r.idEvento}" />
				</c:url>
				<li><a href="${urlDetalle}">${r.getLocal().getNome()} vs
						${r.getVisitante().getNome()} - ${r.getFecha()}</a></li>
			</c:forEach>

		</ul>


<%-- 		<!-- Paxinacion -->
		<p>
		<center>

			<c:url var="urlBase" value="/transportista" scope="page">
				<c:param name="action" value="search" />
				<c:param name="nombre" value="${nombre}" />
				<!--  y asi todos los parametros de la busqueda anterior ... -->
			</c:url>

			<!-- A la anterior pagina -->
			<c:if test="${page > 1}">
				<a href="${urlBase}&page=${page - 1}"> <fmt:message
						key="Anterior" bundle="${messages}" />
				</a>
			&nbsp;&nbsp;
			</c:if>

			<c:if test="${totalPages > 1}">

				<c:if test="${firstPagedPage > 2}">
					<a href="${urlBase}&page=1"><b>1</b></a>
					<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b>
				</c:if>

				<c:forEach begin="${firstPagedPage}" end="${lastPagedPage}" var="i">
					<c:choose>
						<c:when test="${page != i}">
					&nbsp;<a href="${urlBase}&page=${i}"><b>${i}</b></a>&nbsp;
			  </c:when>
						<c:otherwise>
					&nbsp;<b>${i}</b>&nbsp;
			  </c:otherwise>
					</c:choose>
				</c:forEach>

				<c:if test="${lastPagedPage < totalPages-1}">
					<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b>
					<a href="${urlBase}&page=${totalPages}"><b>${totalPages}</b></a>
				</c:if>

			</c:if>

			<!-- A la siguiente página -->
			<c:if test="${page < totalPages}">
				&nbsp;&nbsp;		
				<a href="${urlBase}&page=${page + 1}"> <fmt:message
						key="Siguiente" bundle="${messages}" />
				</a>
			</c:if> --%>
	</c:if>


</div>

