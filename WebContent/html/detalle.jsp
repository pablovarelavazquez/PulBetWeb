<%@include file="/html/common/header.jsp"%>
<%@include file="buscador-page.jsp"%>
<%@include file="carrito.jsp"%>
<div id="home">
<div id="contenido">
<div class="results">
	<p class="tituloresult"><fmt:message key="detail" bundle="${messages}" /></p>
	<div class="detalleeven">
	<p><fmt:message key="detfecha" bundle="${messages}" /> ${DateUtils.WITH_HOUR_FORMAT.format(evento.getFecha())}</p>
	<p><fmt:message key="sport" bundle="${messages}" /> ${deporte.getNome()}</p>
	<p><fmt:message key="competition" bundle="${messages}" /> ${competicion.getNome()}</p>
	</div>
	<p class="titulodetalle">${evento.getLocal().getNome()} vs ${evento.getVisitante().getNome()}</p>
	<c:forEach var="tr" items="${evento.getMercados()}">
		<div class="mercado">
			<p class="titulomercado">${tr.getNome()}</p>
			<div class="resultados">
			<c:forEach var="r" items="${tr.getResultados()}">
				<div class="cuota" data-evento="${evento.getIdEvento()}" data-resultado="${r.getIdResultado()}">
					<p>${r.getNombre()} ${r.getCuota()}</p>
				</div>
			</c:forEach>
			</div>
		</div>
	</c:forEach>
</div>
</div>
</div>

<%@include file="/html/common/footer.jsp"%>