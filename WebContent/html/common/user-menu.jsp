<%@ page import="com.pvv.pulbet.model.*, com.pulbet.web.util.*"%>
<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*"%>
<div id="user-menu">

	<%
		Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
	%>

	<c:choose>
		<c:when test="${empty sessionScope['user']}">

			<div id="login">

				<form action="<%=ControllerPaths.USUARIO%>" method="post">
					<%
						List<String> parameterErrors = errors.showErrors(ParameterNames.ACTION);
								for (String error : parameterErrors) {
					%><li><%=error%></li>
					<%
						}
					%>
					<input type="hidden" name="<%=ParameterNames.ACTION%>"
						value="<%=Actions.LOGIN%>" /> <input type="email"
						name="<%=ParameterNames.LOGIN_EMAIL%>" placeholder="Email"
						value="<%=ParamsUtils.getParameter(request, ParameterNames.LOGIN_EMAIL)%>" />
					<%
						parameterErrors = errors.showErrors(ParameterNames.LOGIN_EMAIL);
								for (String error : parameterErrors) {
					%><li><%=error%></li>
					<%
						}
					%>
					<input type="password" placeholder="Contraseña"
						name="<%=ParameterNames.PASSWORD%>" />
					<button type="submit">IR</button>
				</form>
			</div>

			<div id="user-register">
				<a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.PRE_REGISTRO%>">
					Registro
				</a>
			</div>
		</c:when>

		<c:otherwise>
			<!-- usuario autenticado -->
			<div id="usuario">
				<div id="salir">
					<p>Hola ${sessionScope['user'].getNome()}</p>
					<a href="<%=request.getContextPath()%>/usuario?action=logout">
						Salir </a>
				</div>
				<div id="ingresar">
					<p>${sessionScope['user'].getBanco()}euros</p>

					<c:if
						test="${sessionScope['user'].getBanco() <= 0.0 || empty sessionScope['user'].getBanco()}">

						<a
							href="<%=request.getContextPath() + "/"%><%=ViewPaths.INGRESAR%>"
							> Ingresar </a>

					</c:if>
				</div>

			</div>

			<div id="desplegable" class="menudes">
				<img id="userimage" onclick="desplegarMenu()" class="dropbtn"
					src="<%=request.getContextPath()%>/images/user.png">

				<div id="meumenudes" class="menudes-contido">
					<a
						href="<%=request.getContextPath() + "/"%><%=ViewPaths.EDITPROFILE%>">Mi
						perfil</a> <a
						href="<%=request.getContextPath() + "/"%><%=ViewPaths.INGRESAR%>">Ingresar</a>
					<a href="<%=request.getContextPath() + "/"%><%=ViewPaths.RETIRAR%>">Retirar</a>
					<a href="<%=request.getContextPath() + "/"%><%=ViewPaths.HISTORY%>">Historial</a>
					<a
						href="<%=(ControllerPaths.USUARIO + "?")%>
							<%=ParameterNames.ACTION%>=
							<%=Actions.OPENBETS%>">Apuestas
						abiertas</a>

				</div>
			</div>

		</c:otherwise>
	</c:choose>
</div>