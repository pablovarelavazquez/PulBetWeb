<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*, com.pulbet.web.model.*"%>

<%@include file="/html/common/header.jsp"%>



<%
	Usuario usuario = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
%>


<div id="register-form">
	<div class="contenedor-header">
	<h3>
		<fmt:message key="editMessage" bundle="${messages}" />
	</h3>
	</div>
	
	<form class="contenedor" action="<%=ControllerPaths.USUARIO%>" method="post">
		

		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.EDITPROFILE%>" />
		
			<div class = contenedor-grey>
			
			<c:set var="parameterErrors" scope="page"
			value="${errors.showErrors(ParameterNames.ACTION)}" />
			<c:forEach var="e" items="${parameterErrors}">
			<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>
				
			<div class="green">
				<h2>
					<fmt:message key="userInformation" bundle="${messages}" />
				</h2>
			</div>
			
			<label><fmt:message key="name" bundle="${messages}" /> </label> <input class="my-input"
				class="my-input" name="nombre" type="text" value="<%=usuario.getNome()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NOMBRE)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="apelido1" bundle="${messages}" /> </label>
			<input class="my-input" name="apelido1" type="text" value="<%=usuario.getApelido1()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.APELIDO1)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="apelido2" bundle="${messages}" /> </label>
			<input class="my-input" name="apelido2" type="text" value="<%=usuario.getApelido2()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.APELIDO2)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="dni" bundle="${messages}" />
			</label> <input class="my-input" name="dni" type="text" value="<%=usuario.getDNI()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.DNI)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="bbday"
					bundle="${messages}" /> </label> <input class="my-input" name="fnac" type="date"
				value="<%=u.getFechaNacimiento()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.FECHA_NACIMIENTO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="telefono" bundle="${messages}" /> </label>
			<input class="my-input" name="telefono" type="text" value="<%=usuario.getTelefono()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.TELEFONO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="userName" bundle="${messages}" /> </label>
			<input class="my-input" name="nomeusuario" type="text"
				value="<%=usuario.getNomeUsuario()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NOME_USUARIO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>
			
			<label><fmt:message key="email" bundle="${messages}" /> </label> <input class="my-input"
				name="<%=ParameterNames.REG_EMAIL%>" type="email"
				value="<%=usuario.getEmail()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.REG_EMAIL)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>
			
			</div>

		<br>

			<div class="contenedor-grey">
			<div class="green">
				<h2>
					<fmt:message key="address" bundle="${messages}" />
				</h2>
			</div>

			<label><fmt:message key="city" bundle="${messages}" />: </label> <input class="my-input"
				name="cidade" type="text"
				value="<%=usuario.getDireccion().getCiudad()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.CIDADE)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="country" bundle="${messages}" /> </label> 
			
			<select class="my-input"
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
			<select class="my-input" name="provincia" id="editprovincias">

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
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>
			
			<label><fmt:message key="street" bundle="${messages}" /> </label> <input class="my-input"
				name="calle" type="text"
				value="<%=usuario.getDireccion().getCalle()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.CALLE)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="number" bundle="${messages}" /> </label> <input class="my-input"
				name="numero" type="text"
				value="<%=usuario.getDireccion().getNumero()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NUMERO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="postalCode"
					bundle="${messages}" /> </label> <input class="my-input" name="codpostal" type="text"
				value="<%=usuario.getDireccion().getCodPostal()%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.COD_POSTAL)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="piso" bundle="${messages}" /> </label> 
			<c:if test="${not empty sessionScope['user'].getDireccion().getPiso() }">
			<input class="my-input"
				name="piso" type="text"
				value="<%=usuario.getDireccion().getPiso()%>">
			</c:if>
			<c:if test="${empty sessionScope['user'].getDireccion().getPiso() }">
			<input class="my-input"
				name="piso" type="text">
			</c:if>

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.PISO)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label>
			<fmt:message key="letra" bundle="${messages}" /> </label> 
			<c:if test="${not empty sessionScope['user'].getDireccion().getLetra()}">
			<input class="my-input"
				name="letra" type="text"
				value="<%=usuario.getDireccion().getLetra()%>">
			</c:if>
			<c:if test="${empty sessionScope['user'].getDireccion().getLetra()}">
			<input class="my-input"
				name="letra" type="text">
			</c:if>

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.LETRA)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>
		</div>

		<br>
		
		<input class="my-input" type="submit" value=" <fmt:message key="editar" bundle="${messages}"/> ">
	</form>
	
	<hr class="hr">
<br>
	<div class="contenedor">
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<div class = contenedor-grey>	
			<div class="green">
				<h2>
					<fmt:message key="changePass" bundle="${messages}" />
				</h2>
			</div>

			<label><fmt:message key="oldpass" bundle="${messages}" /> </label> <input class="my-input"
				type="hidden" name="<%=ParameterNames.ACTION%>"
				value="<%=Actions.CHANGE_PASSWORD%>" /> <input class="my-input" type="password"
				name="<%=ParameterNames.PASSWORD%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.PASSWORD)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="newpass" bundle="${messages}" /> </label> <input class="my-input"
				class="my-input" type="password" name="<%=ParameterNames.NEW_PASSWORD%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.NEW_PASSWORD)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>

			<label><fmt:message key="repeatpass" bundle="${messages}" />
			</label> <input class="my-input" type="password" name="<%=ParameterNames.REPEAT_PASSWORD%>">

			<c:set var="parameterErrors" scope="page"
				value="${errors.showErrors(ParameterNames.REPEAT_PASSWORD)}" />
			<c:forEach var="e" items="${parameterErrors}">
				<li class="fallada"><fmt:message key="${e}" bundle="${messages}" /></li>
			</c:forEach>
			</div>
			<br>
			<input class="my-input" type="submit" value=" <fmt:message key="cambiar" bundle="${messages}"/> ">
	</form>
	</div>
</div>

<%@include file="/html/common/footer.jsp"%>
