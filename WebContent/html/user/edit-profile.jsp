<%@ page import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*, com.pulbet.web.model.*" %>

<%@include file="/html/common/header.jsp"%>



<%
	Usuario usuario = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			
%>


<div id="register-form">
		<h3>Editar usuario</h3>	
		
		<form action="<%=ControllerPaths.USUARIO%>"  method="post">
			<%
				List<String> parameterErrors = errors.showErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
			
		<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.EDITPROFILE%>"/>
		<br>
		<fieldset>
		<legend>Informacion de usuario</legend>
		<label>Nombre: </label>
		<input name="nombre" type="text" placeholder="Nome" value="<%=usuario.getNome()%>"> 
				<%
					parameterErrors = errors.showErrors(ParameterNames.NOMBRE);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<label>Email: </label>
		<input name="<%=ParameterNames.REG_EMAIL%>" type="email" placeholder="Email" value="<%=usuario.getEmail()%>">
		<%
					parameterErrors = errors.showErrors(ParameterNames.REG_EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<label>Apellido 1: </label>
		<input name="apelido1" type="text" placeholder="Apelido1" value="<%=usuario.getApelido1()%>">
				<%
					parameterErrors = errors.showErrors(ParameterNames.APELIDO1);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		
		<label>Apellido 2: </label>
		<input name="apelido2" type="text" placeholder="Apelido2" value="<%=usuario.getApelido2()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.APELIDO2);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<br>
		<label>Contraseña: </label>
		<input name="password" type="password" placeholder="Contraseña" value="<%=usuario.getPassword()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.PASSWORD);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<label>Fecha de Nacimiento: </label>
		<input name="fnac" type="text" placeholder="Fecha Nacimiento" value="<%=usuario.getFechaNacimiento()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.FECHA_NACIMIENTO);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<label>Telefono: </label>
		<input name="telefono" type="text" placeholder="Telefono" value="<%=usuario.getTelefono()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.TELEFONO);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<label>Nombre de Usuario: </label>
		<input name="nomeusuario" type="text" placeholder="Nome Usuario" value="<%=usuario.getNomeUsuario()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.NOME_USUARIO);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<br>
		<label>DNI: </label>
		<input name="dni" type="text" placeholder="DNI" value="<%=usuario.getDNI()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.DNI);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		</fieldset>
		<br>
		<br>
		<br>
		<fieldset>
		<legend>Direccion</legend>
		
		<label>Ciudad: </label>
		<input name="cidade" type="text" placeholder="Cidade" value="<%=usuario.getDireccion().getCiudad()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.CIDADE);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<label>Provincia: </label>
		<input name="provincia" type="text" placeholder="Provincia" value="<%=usuario.getDireccion().getIdProvincia()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.PROVINCIA);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<label>Calle: </label>
		<input name="calle" type="text" placeholder="Calle" value="<%=usuario.getDireccion().getCalle()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.CALLE);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<label>Numero: </label>
		<input name="numero" type="text" placeholder="Numero" value="<%=usuario.getDireccion().getNumero()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.NUMERO);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<br>
		<label>Codigo postal: </label>
		<input name="codpostal" type="text" placeholder="Codigo Postal" value="<%=usuario.getDireccion().getCodPostal()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.COD_POSTAL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<label>Piso: </label>
		<input name="piso" type="text" placeholder="Piso" value="<%=usuario.getDireccion().getPiso()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.PISO);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<label>Letra: </label>
		<input name="letra" type="text" placeholder="Letra" value="<%=usuario.getDireccion().getLetra()%>">
						<%
					parameterErrors = errors.showErrors(ParameterNames.LETRA);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		</fieldset>
		<br>
		<br>
		<input type="submit" value="Editar">
		</form>
</div>

<%@include file="/html/common/footer.jsp"%>	