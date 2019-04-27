<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*"%>

<%@include file="/html/common/header.jsp"%>


<div id="findhistorial">
<form action="<%=ControllerPaths.USUARIO%>" method="post">
	<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.HISTORY%>"> 
	<input class="historycheck" type="radio" name="fecha" value="1"> Últimas 24 horas
	<input class="historycheck" type="radio" name="fecha" value="2"> Últimas 48 horas
	
	
	<input class="historycheck" type="radio" name="fecha" value="3" checked>
	<div id="findfecha">
	<label>Desde</label><input type="date" name="fechadesde">
	<label>Hasta</label><input type="date" name="fechahasta">
	</div>
	
	<button type="submit">Buscar</button>
</form>
</div>


<%@include file="/html/user/history.jsp"%>
<%@include file="/html/common/footer.jsp"%>