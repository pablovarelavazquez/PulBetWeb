<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*, com.pulbet.web.model.*"%>

<%@include file="/html/common/header.jsp"%>



<%
	Usuario usuario = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
%>


<div id="register-form">
	<h3>Editar usuario</h3>

	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<%
			List<String> parameterErrors = errors.showErrors(ParameterNames.ACTION);
			for (String error : parameterErrors) {
		%><li><%=error%></li>
		<%
			}
		%>

		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.EDITPROFILE%>" /> <br>
		<fieldset>
			<legend>Informacion de usuario</legend>
			<label>Nombre: </label> <input name="nombre" type="text"
				placeholder="Nome" value="<%=usuario.getNome()%>">
			<%
				parameterErrors = errors.showErrors(ParameterNames.NOMBRE);
				for (String error : parameterErrors) {
			%><li><%=error%></li>
			<%
				}
			%>
			<label>Email: </label> <input name="<%=ParameterNames.REG_EMAIL%>"
				type="email" placeholder="Email" value="<%=usuario.getEmail()%>">
			<%
				parameterErrors = errors.showErrors(ParameterNames.REG_EMAIL);
				for (String error : parameterErrors) {
			%><li><%=error%></li>
			<%
				}
			%>
			<label>Apellido 1: </label> <input name="apelido1" type="text"
				placeholder="Apelido1" value="<%=usuario.getApelido1()%>">
			<%
				parameterErrors = errors.showErrors(ParameterNames.APELIDO1);
				for (String error : parameterErrors) {
			%><li><%=error%></li>
			<%
				}
			%>

			<label>Apellido 2: </label> <input name="apelido2" type="text"
				placeholder="Apelido2" value="<%=usuario.getApelido2()%>">
			<%
				parameterErrors = errors.showErrors(ParameterNames.APELIDO2);
				for (String error : parameterErrors) {
			%><li><%=error%></li>
			<%
				}
			%>
			<br> <label>Fecha de Nacimiento: </label> <input name="fnac"
				type="date" placeholder="Fecha Nacimiento"
				value="<%=u.getFechaNacimiento()%>">
			<%
				parameterErrors = errors.showErrors(ParameterNames.FECHA_NACIMIENTO);
				for (String error : parameterErrors) {
			%><li><%=error%></li>
			<%
				}
			%>
			<label>Telefono: </label> <input name="telefono" type="text"
				placeholder="Telefono" value="<%=usuario.getTelefono()%>">
			<%
				parameterErrors = errors.showErrors(ParameterNames.TELEFONO);
				for (String error : parameterErrors) {
			%><li><%=error%></li>
			<%
				}
			%>
			<label>Nombre de Usuario: </label> <input name="nomeusuario"
				type="text" placeholder="Nome Usuario"
				value="<%=usuario.getNomeUsuario()%>">
			<%
				parameterErrors = errors.showErrors(ParameterNames.NOME_USUARIO);
				for (String error : parameterErrors) {
			%><li><%=error%></li>
			<%
				}
			%>
			<br> <label>DNI: </label> <input name="dni" type="text"
				placeholder="DNI" value="<%=usuario.getDNI()%>">
			<%
				parameterErrors = errors.showErrors(ParameterNames.DNI);
				for (String error : parameterErrors) {
			%><li><%=error%></li>
			<%
				}
			%>
		</fieldset>
		<br> <br> <br>
		<fieldset>
			<legend>Direccion</legend>

			<label>Ciudad: </label> <input name="cidade" type="text"
				placeholder="Cidade" value="<%=usuario.getDireccion().getCiudad()%>">
			<%
				parameterErrors = errors.showErrors(ParameterNames.CIDADE);
				for (String error : parameterErrors) {
			%><li><%=error%></li>
			<%
				}
			%>

			<label>Pais: </label> <select name="pais" id="editpaises">
				<c:forEach var="p" items="${paises}">
					<c:if test="${p.getIdPais() == pais}">
						<option value="${p.getIdPais()}" selected>${p.getNome()}</option>
					</c:if>
					<c:if test="${p.getIdPais() != pais}">
						<option value="${p.getIdPais()}">${p.getNome()}</option>
					</c:if>
				</c:forEach>
			</select> <label>Provincia: </label> <select name="provincia"
				id="editprovincias">
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
			</select> <label>Calle: </label> <input name="calle" type="text"
				placeholder="Calle" value="<%=usuario.getDireccion().getCalle()%>">
			<%
					parameterErrors = errors.showErrors(ParameterNames.CALLE);
					for (String error : parameterErrors) {
				%><li><%=error%></li>
			<%
					}
				%>
			<label>Numero: </label> <input name="numero" type="text"
				placeholder="Numero" value="<%=usuario.getDireccion().getNumero()%>">
			<%
					parameterErrors = errors.showErrors(ParameterNames.NUMERO);
					for (String error : parameterErrors) {
				%><li><%=error%></li>
			<%
					}
				%>
			<br> <label>Codigo postal: </label> <input name="codpostal"
				type="text" placeholder="Codigo Postal"
				value="<%=usuario.getDireccion().getCodPostal()%>">
			<%
					parameterErrors = errors.showErrors(ParameterNames.COD_POSTAL);
					for (String error : parameterErrors) {
				%><li><%=error%></li>
			<%
					}
				%>
			<label>Piso: </label> <input name="piso" type="text"
				placeholder="Piso" value="<%=usuario.getDireccion().getPiso()%>">
			<%
					parameterErrors = errors.showErrors(ParameterNames.PISO);
					for (String error : parameterErrors) {
				%><li><%=error%></li>
			<%
					}
				%>
			<label>Letra: </label> <input name="letra" type="text"
				placeholder="Letra" value="<%=usuario.getDireccion().getLetra()%>">
			<%
					parameterErrors = errors.showErrors(ParameterNames.LETRA);
					for (String error : parameterErrors) {
				%><li><%=error%></li>
			<%
					}
				%>

		</fieldset>
		<br> <br> <input type="submit" value="Editar">
	</form>

	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<fieldset>
			<legend>Cambiar contraseña</legend>
			
			<input type="hidden" name="<%=ParameterNames.ACTION%>"
				value="<%=Actions.CHANGE_PASSWORD%>" /> 
				
			<input type="password"
				name="<%=ParameterNames.PASSWORD%>" placeholder="Antigua contraseña">
			<%
					parameterErrors = errors.showErrors(ParameterNames.PASSWORD);
					for (String error : parameterErrors) {
				%><li><%=error%></li>
			<%
					}
				%>	
				
			<input type="password"
				name="<%=ParameterNames.NEW_PASSWORD%>" placeholder="Nueva contraseña">
			<%
					parameterErrors = errors.showErrors(ParameterNames.NEW_PASSWORD);
					for (String error : parameterErrors) {
				%><li><%=error%></li>
			<%
					}
				%>

			<input type="password" name="<%=ParameterNames.REPEAT_PASSWORD%>"
				placeholder="Repetir contraseña"> 
			<%
					parameterErrors = errors.showErrors(ParameterNames.REPEAT_PASSWORD);
					for (String error : parameterErrors) {
				%><li><%=error%></li>
			<%
					}
				%>	
				<input type="submit"
				value="Cambiar">
		</fieldset>
	</form>
</div>

<%@include file="/html/common/footer.jsp"%>
