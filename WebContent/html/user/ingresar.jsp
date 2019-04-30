<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*"%>

<%@include file="/html/common/header.jsp"%>

<div id="divbanco">

	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	
		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.ACTION)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.INGRESAR%>" />

		<div class="cuadroform">
			<label><fmt:message key="iban" bundle="${messages}" /></label> 
			<input type="text"
				name="<%=ParameterNames.IBAN%>" placeholder="Iban" />
				
			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.IBAN)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>
			
		</div>
		<div class="cuadroform">
			<label><fmt:message key="cantidad" bundle="${messages}" /> </label> 
			<input type="text" placeholder="1.0"
				name="<%=ParameterNames.CANTIDAD%>" />
				
			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.CANTIDAD)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>
		</div>

		<button type="submit"><fmt:message key="ingresar" bundle="${messages}" /></button>
	</form>

</div>


<%@include file="/html/common/footer.jsp"%>