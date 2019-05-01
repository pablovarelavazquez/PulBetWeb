<%@ page
	import="com.pvv.pulbet.model.*, java.util.*,  com.pulbet.web.controller.* , com.pvv.pulbet.service.*"%>

<%@include file="/html/common/header.jsp"%>


<div id="findhistorial">
<form action="<%=ControllerPaths.USUARIO%>" method="post">
	<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.HISTORY%>"> 
	<input class="historycheck" type="radio" name="fecha" value="1"><label><fmt:message key="ult24" bundle="${messages}" /></label>
	<input class="historycheck" type="radio" name="fecha" value="2"><label><fmt:message key="ult48" bundle="${messages}" /></label>
	
	<input class="historycheck" type="radio" name="fecha" value="3" checked>
	<div id="findfecha">
	<label><fmt:message key="desde" bundle="${messages}" /> </label><input class="my-little-input" type="date" name="fechadesde">
	<label><fmt:message key="hasta" bundle="${messages}" /> </label><input class="my-little-input" type="date" name="fechahasta">
	</div>
	
	<input  class="my-button" type="submit" value="<fmt:message key="find" bundle="${messages}" />">
</form>
</div>


<%@include file="/html/user/history.jsp"%>
<%@include file="/html/common/footer.jsp"%>