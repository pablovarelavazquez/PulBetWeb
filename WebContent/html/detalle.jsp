<%@include file="/html/common/header.jsp"%>
<%@include file="buscador-page.jsp"%>
<%@include file="carrito.jsp"%>
<div id="detalle">
	<p class="titulodetalle">${evento.getLocal().getNome()} vs ${evento.getVisitante().getNome()}</p>
	<c:forEach var="tr" items="${evento.getMercados()}">
		<div class="mercado">
			<p class="titulodetalle">${tr.getNome()}</p>
			<c:forEach var="r" items="${tr.getResultados()}">
				<div class="resultado">
					<p>${r.getNombre()}</p>

					<a class="cuota" data-evento="${evento.getIdEvento()}"
						data-resultado="${r.getIdResultado()}">${r.getCuota()}</a>
				</div>
			</c:forEach>
		</div>
	</c:forEach>

</div>

<%@include file="/html/common/footer.jsp"%>