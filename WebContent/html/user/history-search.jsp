<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*"%>

<%@include file="/html/common/header.jsp"%>


<div id="findhistorial">
<form action="<%=ControllerPaths.USUARIO%>" method="post">
	<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.HISTORY%>"> 
	<input type="checkbox" name="fecha" value="1"> Últimas 24 horas
	<input type="checkbox" name="fecha" value="2"> Últimas 48 horas
	
	<div id="findfecha">
	<label>Desde</label><input type="date" name="desde">
	<label>Hasta</label><input type="date" name="hasta">
	</div>
	
	<button type="submit">Buscar</button>
</form>
</div>


<%@include file="/html/user/history.jsp"%>
<%@include file="/html/common/footer.jsp"%>