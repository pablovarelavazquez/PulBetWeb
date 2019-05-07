<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*"%>

<%@include file="/html/common/header.jsp"%>

<div id="divbanco">

	<div class="contenedor-grey">

		<div class="green">
			<h2>
				<fmt:message key="retirar" bundle="${messages}" />
			</h2>
		</div>

		<form action="<%=ControllerPaths.USUARIO%>" method="post">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.ACTION)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}"
						bundle="${messages}" /></li>
			</c:forEach>

			<input type="hidden" name="<%=ParameterNames.ACTION%>"
				value="<%=Actions.RETIRAR%>" /> <label><fmt:message
					key="iban" bundle="${messages}" /> </label> <input class="my-input"
				type="text" name="<%=ParameterNames.IBAN%>" placeholder="Iban" />

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.IBAN)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}"
						bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="cantidad" bundle="${messages}" />:
			</label> <input class="my-input" type="text" placeholder="1.0"
				name="<%=ParameterNames.CANTIDAD%>" />
			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.CANTIDAD)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}"
						bundle="${messages}" /></li>
			</c:forEach>

			<p>
				<fmt:message key="maxcant" bundle="${messages}" />
				${sessionScope['user'].getBanco()}
			</p>

			<input class="my-input" type="submit"
				value="<fmt:message key="retbttn" bundle="${messages}" />">
		</form>

	</div>
</div>


<%@include file="/html/common/footer.jsp"%>