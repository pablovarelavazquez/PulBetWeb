<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*"%>

<%@include file="/html/common/header.jsp"%>

<div id="register-form w3-container">
	<h3>
		<fmt:message key="regmess" bundle="${messages}" />
	</h3>

	<form class="w3-container" action="<%=ControllerPaths.USUARIO%>"
		method="post">

		<div class="w3-card-4">
			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.ACTION)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<input type="hidden" name="<%=ParameterNames.ACTION%>"
				value="<%=Actions.REGISTRO%>" /> <br>
			<div class="w3-container w3-green">
				<h2>
					<fmt:message key="userInformation" bundle="${messages}" />
				</h2>
			</div>

			<label><fmt:message key="name" bundle="${messages}" /> </label> <input
				class="w3-input" name="nombre" type="text">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NOMBRE)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="email" bundle="${messages}" /></label> <input
				class="w3-input" name="<%=ParameterNames.REG_EMAIL%>" type="email">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.REG_EMAIL)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="apelido1" bundle="${messages}" /> </label>
			<input class="w3-input" name="apelido1" type="text">
			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.APELIDO1)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="apelido2" bundle="${messages}" /> </label>
			<input class="w3-input" name="apelido2" type="text">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.APELIDO2)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="password" bundle="${messages}" /> </label>
			<input class="w3-input" name="password" type="password">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.PASSWORD)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>


			<label><fmt:message key="repeatpass" bundle="${messages}" />
			</label> <input class="w3-input" type="password" name="<%=ParameterNames.REPEAT_PASSWORD%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.REPEAT_PASSWORD)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>


			<label><fmt:message key="bbday" bundle="${messages}" /> </label> <input
				name="fnac" type="date">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.FECHA_NACIMIENTO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="telefono" bundle="${messages}" /> </label>
			<input class="w3-input" name="telefono" type="text">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.TELEFONO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="userName" bundle="${messages}" /> </label>
			<input class="w3-input" name="nomeusuario" type="text">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NOME_USUARIO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="dni" bundle="${messages}" /></label> <input
				name="dni" type="text">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.DNI)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

		</div>
		<br> <br> <br>
		<fieldset>
			<legend>
				<fmt:message key="address" bundle="${messages}" />
			</legend>

			<label><fmt:message key="city" bundle="${messages}" />: </label> <input
				name="cidade" type="text">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.CIDADE)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="country" bundle="${messages}" /> </label> <select
				name="pais" id="paises">
				<c:forEach var="p" items="${paises}">
					<c:if test="${p.getIdPais() == 34}">
						<option value="${p.getIdPais()}" selected>${p.getNome()}</option>
					</c:if>
					<c:if test="${p.getIdPais() != 34}">
						<option value="${p.getIdPais()}">${p.getNome()}</option>
					</c:if>
				</c:forEach>
			</select> <label><fmt:message key="provincia" bundle="${messages}" /></label>
			<select name="provincia" id="provincias">
				<c:forEach var="p" items="${provincias}">
					<option value="${p.getIdProvincia()}">${p.getNome()}</option>
				</c:forEach>
			</select>

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.PROVINCIA)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="street" bundle="${messages}" /></label> <input
				name="calle" type="text">
			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.CALLE)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="number" bundle="${messages}" /></label> <input
				name="numero" type="text">
			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NUMERO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>


			<label><fmt:message key="postalCode" bundle="${messages}" /></label>
			<input name="codpostal" type="text">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.COD_POSTAL)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="piso" bundle="${messages}" /></label> <input
				name="piso" type="text">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.PISO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="letra" bundle="${messages}" /></label> <input
				name="letra" type="text">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.LETRA)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

		</fieldset>
		<br> <br> <input type="submit"
			value="<fmt:message key="registrar" bundle="${messages}"/> ">
	</form>
</div>

<%@include file="/html/common/footer.jsp"%>

