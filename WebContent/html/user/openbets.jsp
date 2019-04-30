<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.*, com.pulbet.web.util.* , com.pvv.pulbet.service.*"%>

<%@include file="/html/common/header.jsp"%>

<div id="history">
	<c:if test="${empty apuestas}">
		<p><fmt:message key="notOpenBets" bundle="${messages}" /></p>
	</c:if>
	<c:if test="${not empty apuestas}">
		<c:forEach var="a" items="${apuestas}">

			<div class="apuestahistorial">
				<p class="fechaapuesta">${DateUtils.WITH_HOUR_FORMAT.format(a.getFecha())}</p>
				<p class="idapuesta">
					<fmt:message key="betId" bundle="${messages}" />
					${a.getIdApuesta()}
				</p>
				<c:if test="${a.getLineas().size() > 1}">
					<p>
						<fmt:message key="combin" bundle="${messages}">
							<fmt:param value="${a.getLineas().size()}" />
						</fmt:message>
					</p>
				</c:if>
				<c:if test="${a.getLineas().size() <= 1}">
					<p><fmt:message key="simple" bundle="${messages}" /></p>
				</c:if>
				<p>
					<fmt:message key="cuotas" bundle="${messages}" />
					${a.getGanancias() / a.getImporte()}
				</p>
				<p>
					<fmt:message key="import" bundle="${messages}" />
					${a.getImporte()}
				</p>
				<div class="masdetalle" data-id="${a.getIdApuesta()}">
					<p>
						<fmt:message key="details" bundle="${messages}" />
					</p>
					<div class="fillo"></div>
				</div>

			</div>
		</c:forEach>
	</c:if>

</div>
<%@include file="/html/common/footer.jsp"%>