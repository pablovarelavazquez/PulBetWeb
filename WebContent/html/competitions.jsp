<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*"%>

<div class="results">



	<c:if test="${not empty competiciones}">
			<p>Competiciones</p>
		
		<ul>
			<c:forEach var="comp" items="${competiciones}">


				<c:url var="url" scope="page" value="evento">
					<c:param name="action" value="<%=Actions.BUSCADOR%>" />
					<c:param name="competicion" value="${comp.idCompeticion}" />
					<c:param name="deporte" value="${comp.idDeporte}" />
				</c:url>
				<li><a class="competicion" href="${url}">${comp.getNome()}</a></li>
			</c:forEach>

		</ul>


 	</c:if>

</div>

