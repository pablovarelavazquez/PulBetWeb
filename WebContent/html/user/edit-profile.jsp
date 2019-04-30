<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*, com.pulbet.web.model.*"%>

<%@include file="/html/common/header.jsp"%>



<%
	Usuario usuario = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
%>


<div id="register-form">
	<h3>
		<fmt:message key="editMessage" bundle="${messages}" />
	</h3>

	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.ACTION)}" />
		<c:forEach var="e" items="${parameterErrors}">
			<li><fmt:message key="${e}" bundle="${messages}" /></li>
		</c:forEach>

		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.EDITPROFILE%>" /> <br>
		<fieldset>
			<legend>
				<fmt:message key="userInformation" bundle="${messages}" />
			</legend>
			
			<label><fmt:message key="name" bundle="${messages}" /> </label> <input
				name="nombre" type="text" value="<%=usuario.getNome()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NOMBRE)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="email" bundle="${messages}" /> </label> <input
				name="<%=ParameterNames.REG_EMAIL%>" type="email"
				value="<%=usuario.getEmail()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.REG_EMAIL)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="apelido1" bundle="${messages}" /> </label>
			<input name="apelido1" type="text" value="<%=usuario.getApelido1()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.APELIDO1)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="apelido2" bundle="${messages}" /> </label>
			<input name="apelido2" type="text" value="<%=usuario.getApelido2()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.APELIDO2)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<br> <label><fmt:message key="bbday"
					bundle="${messages}" /> </label> <input name="fnac" type="date"
				value="<%=u.getFechaNacimiento()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.FECHA_NACIMIENTO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>


			<label><fmt:message key="telefono" bundle="${messages}" /> </label>
			<input name="telefono" type="text" value="<%=usuario.getTelefono()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.TELEFONO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="userName" bundle="${messages}" /> </label>
			<input name="nomeusuario" type="text"
				value="<%=usuario.getNomeUsuario()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NOME_USUARIO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<br> <label><fmt:message key="dni" bundle="${messages}" />
			</label> <input name="dni" type="text" value="<%=usuario.getDNI()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.DNI)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

		</fieldset>

		<br> <br> <br>

		<fieldset>
			<legend>
				<fmt:message key="address" bundle="${messages}" />
			</legend>

			<label><fmt:message key="city" bundle="${messages}" />: </label> <input
				name="cidade" type="text"
				value="<%=usuario.getDireccion().getCiudad()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.CIDADE)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="country" bundle="${messages}" /> </label> 
			
			<select
				name="pais" id="editpaises">

				<c:forEach var="p" items="${paises}">
					<c:if test="${p.getIdPais() == pais}">
						<option value="${p.getIdPais()}" selected>${p.getNome()}</option>
					</c:if>
					<c:if test="${p.getIdPais() != pais}">
						<option value="${p.getIdPais()}">${p.getNome()}</option>
					</c:if>
				</c:forEach>

			</select> 
			<label><fmt:message key="provincia" bundle="${messages}" /></label> 
			<select name="provincia" id="editprovincias">

				<c:forEach var="p" items="${provincias}">
					<c:if
						test="${p.getIdProvincia() == sessionScope['user'].getDireccion().getIdProvincia()}">
						<option value="${p.getIdProvincia()}" selected>${p.getNome()}</option>
					</c:if>
					<c:if
						test="${p.getIdProvincia() != sessionScope['user'].getDireccion().getIdProvincia()}">
						<option value="${p.getIdProvincia()}">${p.getNome()}</option>
					</c:if>

				</c:forEach>
			</select> 
			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.PROVINCIA)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>
			
			<label><fmt:message key="street" bundle="${messages}" /> </label> <input
				name="calle" type="text"
				value="<%=usuario.getDireccion().getCalle()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.CALLE)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="number" bundle="${messages}" /> </label> <input
				name="numero" type="text"
				value="<%=usuario.getDireccion().getNumero()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NUMERO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<br> <label><fmt:message key="postalCode"
					bundle="${messages}" /> </label> <input name="codpostal" type="text"
				value="<%=usuario.getDireccion().getCodPostal()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.COD_POSTAL)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="piso" bundle="${messages}" /> </label> 
			<c:if test="${not empty sessionScope['user'].getDireccion().getPiso() }">
			<input
				name="piso" type="text"
				value="<%=usuario.getDireccion().getPiso()%>">
			</c:if>
			<c:if test="${empty sessionScope['user'].getDireccion().getPiso() }">
			<input
				name="piso" type="text">
			</c:if>

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.PISO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label>
			<fmt:message key="letra" bundle="${messages}" /> </label> 
			<c:if test="${not empty sessionScope['user'].getDireccion().getLetra()}">
			<input
				name="letra" type="text"
				value="<%=usuario.getDireccion().getPiso()%>">
			</c:if>
			<c:if test="${empty sessionScope['user'].getDireccion().getLetra()}">
			<input
				name="letra" type="text">
			</c:if>

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.LETRA)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

		</fieldset>
		<br> <br> <input type="submit" value=" <fmt:message key="editar" bundle="${messages}"/> ">
	</form>

	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<fieldset>
			<legend>
				<fmt:message key="changePass" bundle="${messages}" />
			</legend>

			<label><fmt:message key="oldpass" bundle="${messages}" /> </label> <input
				type="hidden" name="<%=ParameterNames.ACTION%>"
				value="<%=Actions.CHANGE_PASSWORD%>" /> <input type="password"
				name="<%=ParameterNames.PASSWORD%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.PASSWORD)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="newpass" bundle="${messages}" /> </label> <input
				type="password" name="<%=ParameterNames.NEW_PASSWORD%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NEW_PASSWORD)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="repeatpass" bundle="${messages}" />
			</label> <input type="password" name="<%=ParameterNames.REPEAT_PASSWORD%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.REPEAT_PASSWORD)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<input type="submit" value=" <fmt:message key="cambiar" bundle="${messages}"/> ">
		</fieldset>
	</form>
</div>

<%@include file="/html/common/footer.jsp"%>
