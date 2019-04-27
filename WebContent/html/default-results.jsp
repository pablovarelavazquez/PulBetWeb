<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*,  com.pulbet.web.util.* "%>

<div class="results">

	<c:if test="${(not empty eventos) && (empty resultados)}">
	
		<p>Proximos eventos:</p>
		
		<c:if test="${not empty eventos.getPage()}">
			
				<c:forEach var="e" items="${eventos.getPage()}">
	
					<c:url var="urlDetalle" scope="page" value="evento">
						<c:param name="action" value="<%=Actions.FIND_DETAIL%>" />
						<c:param name="id" value="${e.idEvento}" />
					</c:url>
					
					<c:set var="date" scope="page" value="${DateUtils.WITH_HOUR_FORMAT.format(e.getFecha())}"/>
					<c:set var="mercado" scope="page" value="${e.getMercados().get(0)}" />
					<c:set var="resultados" scope="page" value="${mercado.getResultados()}" />
					
					
					<a class="titulodetalle" class="mercado" href="${urlDetalle}">${e.getLocal().getNome()} vs
							${e.getVisitante().getNome()} - ${date}</a>
							
					<div class="mercado">
					<p class="titulodetalle">${mercado.getNome()}</p>
					<c:forEach var="resultado" items="${resultados}">

						<div class="resultado">
							<p>${resultado.getNombre()}</p>

							<a class="cuota" data-evento="${resultado.idEvento}" data-resultado="${resultado.idResultado}">${resultado.getCuota()}</a>
						</div>
					</c:forEach>
				</div>
							
							
							
				</c:forEach>
			
			
			
		</c:if>
		

	</c:if>
	
</div>