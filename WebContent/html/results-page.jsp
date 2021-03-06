<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*, com.pulbet.web.util.*"%>

<c:if test="${resultados != null}">
	<c:if test="${empty resultados}">
	<div class="contenedor-header">
	<p class="pendiente">
		<fmt:message key="noresults" bundle="${messages}" />
	</p>
	</div>
	</c:if>
</c:if>

<div class="results">
	<c:if test="${not empty resultados}">

		<p class="tituloresult">
			<fmt:message key="resultados" bundle="${messages}" />:
		</p>



		<c:forEach var="r" items="${resultados}">


			<c:url var="urlDetalle" scope="page" value="evento">
				<c:param name="action" value="<%=Actions.FIND_DETAIL%>" />
				<c:param name="id" value="${r.idEvento}" />
			</c:url>
			<c:set var="date" scope="page"
				value="${DateUtils.WITH_HOUR_FORMAT.format(r.getFecha())}" />
			<c:set var="mercado" scope="page" value="${r.getMercados().get(0)}" />
			<c:set var="resultados" scope="page"
				value="${mercado.getResultados()}" />

			<a class="titulodetalle" class="mercado" href="${urlDetalle}">${r.getLocal().getNome()}
				vs ${r.getVisitante().getNome()} - ${date}</a>
			<div class="mercado">
				<p class="titulomercado">${mercado.getNome()}</p>
				<div class="resultados">
				<c:forEach var="resultado" items="${resultados}">


					<div class="cuota" data-evento="${resultado.idEvento}" data-resultado="${resultado.idResultado}">
						<p>${resultado.getNombre()} ${resultado.getCuota()}</p>
					</div>
				</c:forEach>
				</div>
			</div>

		</c:forEach>




		<!-- Paxinacion -->
		<p>
		<center>

			<!-- A la anterior pagina -->
			<c:if test="${page > 1}">
				<a href="${url}&page=${page - 1}"> 
				<fmt:message key="anterior" bundle="${messages}" />
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




