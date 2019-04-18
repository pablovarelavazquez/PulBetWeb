<%@ page import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*" %>

<%@include file="/html/common/header.jsp"%>

<div id="register-form">
		<h3>Registrate en la mejor casa de apuestas</h3>	
		
		<form action="<%=ControllerPaths.USUARIO%>"  method="post">
			<%
				List<String> parameterErrors = errors.showErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
			
		<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.REGISTRO%>"/>
		<br>
		<fieldset>
		<legend>Informacion de usuario</legend>
		<input name="nombre" type="text" placeholder="Nome"> 
				<%
					parameterErrors = errors.showErrors(ParameterNames.NOMBRE);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="<%=ParameterNames.REG_EMAIL%>" type="email" placeholder="Email">
		<%
					parameterErrors = errors.showErrors(ParameterNames.REG_EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="apelido1" type="text" placeholder="Apelido1">
				<%
					parameterErrors = errors.showErrors(ParameterNames.APELIDO1);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="apelido2" type="text" placeholder="Apelido2">
						<%
					parameterErrors = errors.showErrors(ParameterNames.APELIDO2);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="password" type="password" placeholder="Contraseña">
						<%
					parameterErrors = errors.showErrors(ParameterNames.PASSWORD);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="fnac" type="date" placeholder="Fecha Nacimiento">
						<%
					parameterErrors = errors.showErrors(ParameterNames.FECHA_NACIMIENTO);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="telefono" type="text" placeholder="Telefono">
						<%
					parameterErrors = errors.showErrors(ParameterNames.TELEFONO);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="nomeusuario" type="text" placeholder="Nome Usuario">
						<%
					parameterErrors = errors.showErrors(ParameterNames.NOME_USUARIO);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="dni" type="text" placeholder="DNI">
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
		<input name="cidade" type="text" placeholder="Cidade">
						<%
					parameterErrors = errors.showErrors(ParameterNames.CIDADE);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		
		<select name="pais" id="paises">
		<c:forEach var="p" items="${paises}">
			<c:if test="${p.getIdPais() == 34}">
				<option value="${p.getIdPais()}" selected>${p.getNome()}</option>
			</c:if>
			<c:if test="${p.getIdPais() != 34}">
				<option value="${p.getIdPais()}">${p.getNome()}</option>
			</c:if>
		</c:forEach>
		</select>
		
		<select name="provincia" id="provincias">
		<c:forEach var="p" items="${provincias}">
			<option value="${p.getIdProvincia()}">${p.getNome()}</option>
		</c:forEach>
		</select>
		
		<input name="calle" type="text" placeholder="Calle">
						<%
					parameterErrors = errors.showErrors(ParameterNames.CALLE);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="numero" type="text" placeholder="Numero">
						<%
					parameterErrors = errors.showErrors(ParameterNames.NUMERO);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="codpostal" type="text" placeholder="Codigo Postal">
						<%
					parameterErrors = errors.showErrors(ParameterNames.COD_POSTAL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="piso" type="text" placeholder="Piso">
						<%
					parameterErrors = errors.showErrors(ParameterNames.PISO);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		<input name="letra" type="text" placeholder="Letra">
						<%
					parameterErrors = errors.showErrors(ParameterNames.LETRA);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
		%>
		</fieldset>
		<br>
		<br>
		<input type="submit" value="Registrar">
		</form>
</div>

<%@include file="/html/common/footer.jsp"%>	

	