<%@ page import="com.pvv.pulbet.model.*, com.pulbet.web.util.*"%>
<%@ page
	import="java.util.List, com.pulbet.web.util.* , com.pulbet.web.controller.*"%>
<div id="user-menu">

	<%
		Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
		Cookie cookie = CookieManager.getCookie(request, WebConstants.REMEMBERME);
		

	%>
	
	<c:choose>
		<c:when test="${empty sessionScope['user']}">

			<div id="login">

				<form action="<%=ControllerPaths.USUARIO%>" method="post">
					
					<input  type="hidden" name="<%=ParameterNames.ACTION%>"
						value="<%=Actions.LOGIN%>" /> 
						
					
					<% 
					if(cookie!=null){
					String email = cookie.getValue();
					%>
					<input class="logininput" type="email" id="loginemail"
						name="<%=ParameterNames.LOGIN_EMAIL%>" placeholder="Email"
						value="<%=email%>" />
					
					<input class="logininput"  type="password" placeholder="Contraseña"
						name="<%=ParameterNames.PASSWORD%>" />
					
					<button id="loginbutton" type="submit">IR</button>
					
					<div id="remember">
					<input id="rememberinput" type="checkbox" checked="checked">
					<label>Recordar usuario</label>
					</div>
					<% 
					} else {
					%>
					<input class="logininput" type="email" id="loginemail"
						name="<%=ParameterNames.LOGIN_EMAIL%>" placeholder="Email"
						value="<%=ParamsUtils.getParameter(request, ParameterNames.LOGIN_EMAIL)%>" />
						
						<input class="logininput"  type="password" placeholder="Contraseña"
						name="<%=ParameterNames.PASSWORD%>" />
					
					<button id="loginbutton" type="submit">IR</button>
					
					<div id="remember">
					<input id="rememberinput" type="checkbox">
					<label>Recordar usuario</label>
					</div>
					<% 
					}
					%>
					
					
					
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
					
					<c:if
						test="${sessionScope['user'].getBanco() > 0.0 }">

						<p>${sessionScope['user'].getBanco()} euros</p>

					</c:if>

					<c:if
						test="${sessionScope['user'].getBanco() <= 0.0 || empty sessionScope['user'].getBanco()}">
						<p>0.0 euros</p>
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
						href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.PRE_EDIT%>">Mi
						perfil</a> 
					<a
						href="<%=request.getContextPath()%><%=ViewPaths.INGRESAR%>">Ingresar</a>
					<a href="<%=request.getContextPath()%><%=ViewPaths.RETIRAR%>">Retirar</a>
					<a href="<%=request.getContextPath()%><%=ViewPaths.HISTORY%>">Historial</a>
					<a
						href="<%=(ControllerPaths.USUARIO + "?")%>
							<%=ParameterNames.ACTION%>=
							<%=Actions.OPENBETS%>">Apuestas
						abiertas</a>
					<a href="<%=(ControllerPaths.USUARIO + "?")%><%=ParameterNames.ACTION%>=
							<%=Actions.CLOSE_ACCOUNT%>">Cerrar cuenta</a>

				</div>
			</div>

		</c:otherwise>
	</c:choose>
</div>