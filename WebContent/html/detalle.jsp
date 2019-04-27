
<%@include file="/html/common/header.jsp"%>
<%@include file="/html/common/left.jsp"%>
<%@include file="/html/common/right.jsp"%>

<%
	Evento evento = (Evento) request.getAttribute(AttributeNames.EVENTO);
%>

<div id="detalle">

	<p class="titulodetalle"><%=evento.getLocal().getNome()%>
		vs
		<%=evento.getVisitante().getNome()%></p>

	<%
		for (TipoResultado tr : evento.getMercados()) {
	%>
	<div class="mercado">
		<p class="titulodetalle"><%=tr.getNome()%></p>
		<%
			for (Resultado r : tr.getResultados()) {
		%>
		<div class="resultado">
			<p><%=r.getNombre()%></p>
<%-- 			<a
				href="<%=(ControllerPaths.CARRITO + "?")%>
				<%=ParameterNames.ACTION%>=<%=Actions.ADD_CARRITO%>&amp;<%=ParameterNames.ID_EVENTO%>=<%=evento.getIdEvento()%>
				&amp;<%=ParameterNames.ID_RESULTADO%>=<%=r.getIdResultado()%>">
				<%=r.getCuota()%></a> --%>
				
				<a class="cuota" data-evento="<%=evento.getIdEvento()%>" data-resultado="<%=r.getIdResultado()%>"><%=r.getCuota()%></a>
		</div>
		<%
			}
		%>
	</div>
	<%
		}
	%>

</div>

<%@include file="/html/common/footer.jsp"%>