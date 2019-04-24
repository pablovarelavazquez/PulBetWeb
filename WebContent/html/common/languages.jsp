<%@ page
	import="java.util.HashMap, java.util.Map, com.pulbet.web.util.*, com.pulbet.web.controller.*"%>


<div id="languages">



	<c:url var="urlESP" scope="page" value="usuario">
		<c:param name="action" value="<%=Actions.CHANGE_LOCALE%>" />
		<c:param name="locale" value="es-ES" />
		<c:param name="url" value="${url}" />
	</c:url>
	<c:url var="urlGAL" scope="page" value="usuario">
		<c:param name="action" value="<%=Actions.CHANGE_LOCALE%>" />
		<c:param name="locale" value="gl-ES" />
		<c:param name="url" value="${url}" />
	</c:url>
	<c:url var="urlENG" scope="page" value="usuario">
		<c:param name="action" value="<%=Actions.CHANGE_LOCALE%>" />
		<c:param name="locale" value="en-GB" />
		<c:param name="url" value="${url}" />
	</c:url>

	<c:if test="${not empty page}">
		<a href="${urlGAL}&page=${page}"><img
			src="<%=request.getContextPath()%>/images/gal-flag.png"></a>
		<a href="${urlESP}&page=${page}"><img
			src="<%=request.getContextPath()%>/images/esp-flag.png"></a>
		<a href="${urlENG}&page=${page}"><img
			src="<%=request.getContextPath()%>/images/eng-flag.png"></a>
	</c:if>

	<c:if test="${empty page}">
		<a href="${urlGAL}"><img
			src="<%=request.getContextPath()%>/images/gal-flag.png"></a>
		<a href="${urlESP}"><img
			src="<%=request.getContextPath()%>/images/esp-flag.png"></a>
		<a href="${urlENG}"><img
			src="<%=request.getContextPath()%>/images/eng-flag.png"></a>
	</c:if>
</div>

