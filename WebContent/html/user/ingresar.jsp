<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*"%>

<%@include file="/html/common/header.jsp"%>

<div id="divbanco">

		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<%
				List<String> parameterErrors = errors.showErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%><li><%=error%></li>
			<%
				}
			%>
			<input type="hidden" name="<%=ParameterNames.ACTION%>"
				value="<%=Actions.INGRESAR%>" /> 
			
			<div class = "cuadroform">
			<label>IBAN: </label>	
			<input type="text"
				name="<%=ParameterNames.IBAN%>" placeholder="Iban"
			/>
			<%
					parameterErrors = errors.showErrors(ParameterNames.IBAN);
					for (String error: parameterErrors) {
						%><li><%=error%></li>
			<%
					}
				%>
			</div>
			<div class = "cuadroform">
			<label>Cantidad: </label>
			<input type="text" placeholder="1.0"
				name="<%=ParameterNames.CANTIDAD%>"/>
						<%
					parameterErrors = errors.showErrors(ParameterNames.CANTIDAD);
					for (String error: parameterErrors) {
						%><li><%=error%></li>
			<%
					}
				%>
			</div>
				
			<button type="submit">Ingresar</button>
		</form>

</div>


<%@include file="/html/common/footer.jsp"%>